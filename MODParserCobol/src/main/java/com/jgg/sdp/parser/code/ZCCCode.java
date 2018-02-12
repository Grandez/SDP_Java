/**
 * Contiene todas las acciones semanticas independientemente del 
 * dialecto COBOL
 * De esta forma se separa el proceso de analisis de sus acciones
 * reutilizando el codigo
 *  
 * Casos especiales en algun dialecto deberian extender esta clase
 * y sobreescribir las acciones correspondientes
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.code;

/**
 * Tenemos el grafo raiz que solo tiene parrafos
 * Subgrafos por cada parrafo
 */
import java.util.*;

import com.jgg.sdp.blocks.stmt.*;
import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.graph.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.parser.base.NotSupportedException;
import com.jgg.sdp.parser.lang.ZCZSym;
import com.jgg.sdp.parser.stmt.*;
import com.jgg.sdp.parser.symbols.*;
import com.jgg.sdp.parser.tools.*;

import static com.jgg.sdp.parser.lang.ZCCSym.*;
import static com.jgg.sdp.common.ctes.CDG.*;

public class ZCCCode extends ZCZCode {

	private Injector injector = InjectorSingleton.getInjector();
	
	private FactoryGraphs graphs = FactoryGraphs.getInstance(true);
	
	private Graph       rootGraph;
	private SubGraph    grafo;
	
	private Paragraph parrafo = null;

	private Block currBlock = null;
	
	// Pila de controles de flujo: IF, PERFORM, EVALUATE, SEARCH
    private Stack<StmtCobol> stackFlujo = new Stack<StmtCobol>(); 

	private SDPSymbolFactory symbolFactory = new SDPSymbolFactory();

    // Flag para controlar sentencias que admiten imperative-statement
    // Ejemplo:
    //   READ DATO AT END imperative-statement
    private boolean imperative    = false;
    private int     numStatements = 0; // No contempla la actual
    private int     bloques       = 0;
    private boolean skipBlock     = false;

    public ZCCCode(Module module) {
       super(module);
       rootGraph = module.getComponentGraph();
	}

    public SDPSymbol add (SDPSymbol base, SDPSymbol... syms) {
        for (SDPSymbol o : syms) base.add(o);
        return base;    			      
    }

    public int  getNumStatements()      { return numStatements;   }
	
	public void incStmt(StmtCobol stmt) { 
		  numStatements++;        
    }
    
    public void setImperative() { imperative = true; }

    public Variable getVariable(SDPSymbol s) {
    	Variable var = module.getVariable(s);
    	if (var == null) module.setVarMissing();
        return var;

    }
    
    public StmtCobol setAtEnd(StmtCobol stmt) { 
        parrafo.incCiclomatic();
        newBlock(stmt, true, TRAP.ATEND);
        return stmt; 
    }    

	public void beginCode(SDPSymbol s) {
		info.addDivision(CDG.SECT_PRC, s.line);
        Trap trap = new Trap(TRAP.BEG_MODULE, makeLiteral(module.getName()));
        trap = injector.setPrevTrap(trap);

        grafo = newGraph(0, module.getName());
        grafo.addLinearNode(TRAP.BLOCK, 0);
	}
	
	public SDPSymbol makeSymbol(int code, int line, int column, String text) {
		 return (SDPSymbol) symbolFactory.newSymbol(code, line, column, text).value;
	}

	public SDPSymbol makeSymbol(SDPSymbol s, int code, String text) {
		return (SDPSymbol) symbolFactory.newSymbol(code, -1, -1, text).value;
	}
	
	/**
     * Al inicio del proceso se crea un parrafo virtual Este parrafo se aplica
     * si el programa no se inicia en un parrafo y se elimina si se inicia con
     * un parrafo.
     *
     * @param endp
     *            Punto de PROCEDURE DIVISION
     */
	public void   parrafoVirtual(SDPSymbol endp) {
        parrafo = module.addParagraph("", endp.line, 0);		
	}

	public int newBlock(boolean trap, Integer cause) {
		return newBlock(null, trap, cause);
	}
	
	public int newBlock(StmtCobol stmt, boolean genTrap, Integer cause) {
        int bloque = ++bloques;
        int endLine = -1;
		int stmts   = numStatements;
		
		if (stmt != null) {
		    endLine = stmt.getEndLine();
		    stmts++;
		}
		
        currBlock = new Block(bloque, stmts, cause);
		
        module.addBlock(currBlock, endLine);

        if (genTrap) {
		   Trap trap = new Trap(TRAP.BLOCK, bloque);
	       injector.setNextTrap(trap);
        }   
        
        processGraph(stmt, cause);
        
		return bloque;		
	}
    private void processGraph(StmtCobol stmt, Integer cause) {
    	boolean full =  (stmt != null && stmt.endWithPoint()) ? true : false; 
    	switch (cause) {
    	    case TRAP.BEG_MODULE: break;
    	    case TRAP.BLOCK:      grafo.addLinearNode(cause, numStatements); break;
    	    case TRAP.IF:         
    	    case TRAP.EVAL:
    	    case TRAP.SEARCH:     grafo.addBlock(cause, numStatements, stmt.toText()); break;
    	    case TRAP.END_IF:   
    	    case TRAP.END_EVAL:  
    	    case TRAP.END_SEARCH: grafo.endBlock(numStatements, full); break;    	    
    	    case TRAP.ELSE:       
    	    	grafo.addBranch(cause, numStatements, true, null); break;
    	    case TRAP.WHEN:    	
    	    	 boolean close = stmt.getOptionByName("OTHER") != null ? true : false;      
    	    	 grafo.addBranch(cause, numStatements, close, stmt.toText());
//    	   default: grafo.addLinearNode(TRAP.BLOCK, cause, numStatements);    break; 	 
    	}
    }
    
	public StmtCobol processStatement(StmtCobol stmt, StmtCobol last) {

		calculateBlock(stmt, last);
		
		if (imperative) checkPerform(stmt);
		
		// Es la primera sentencia 
		if (last == null && parrafo.isVirtual()) {  // Hay parrafo incial?
		    numStatements--; // Ya se ha contando la instruccion
		    int bloque = newBlock(null, false, TRAP.BEG_MODULE);
		    currBlock.setBegin(stmt.getBegLine());
		    numStatements++;
		    injector.setPrevTrap(new Trap(TRAP.BLOCK, bloque));
		}
		
		if (currBlock.getBegin() == -1 && !skipBlock) {
		    module.beginBlock(stmt.getBegLine());
		}
		
		skipBlock = false;
		
//		injector.loadTraps(stmt.getBegLine(), stmt.getBegColumn());

		imperative = false;
		return stmt;
	}

	private void calculateBlock(StmtCobol stmt, StmtCobol last) {
		// La instruccion se cuenta antes de procesarla
		if (parrafo.getNumStatements() < 2) {
			parrafo.incBlocks();
			return;
		}
		if (last == null) {
			System.out.println("parar");
		}
		if (last.getGroup() == stmt.getGroup()) return;
		
		if (last.getGroup() == STMT_SQL || last.getGroup() == STMT_CICS) {
		    if (stmt.getGroup() != STMT_FLOW) {
		    	parrafo.incBlocks();
			}
		}

	}
	public void checkPerform(StmtCobol stmt) {
		parrafo.incCiclomatic();
		if (stmt.getVerb().sym != NodeTypes.PERFORM) return;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM));
	}
	
	public SDPSymbol processParagraph(SDPSymbol s, StmtCobol last) {
	    boolean firstP = false;
        Trap trap = null;
	    String name = getName(s);
	    
	    if (name.length() > SYS.MAX_PARR_NAME) notSupportedParrName(s);
	    
        // Si es el primer parrafo y no hay sentencias
        // Reemplazar el parrafo virtual
        // Incluir una llamada a ese Perform
	    
        if (numStatements == 0) {
        	module.setFirstParagraph(true);
            parrafo = module.getParagraph("");
            if (parrafo != null) {
                parrafo.setName(name);
                parrafo.setLine(s.line);
                firstP = true;
            }    
            injector.removeTraps();  // Quitar el de inicio de modulo
            trap = new Trap(TRAP.FIRST_PARR, name, module.getName());
            injector.setPrevTrap(trap);

        }         
        
//JGG4        if (numStmts > 0 && !parrafo.isExit()) {  
//JGG4            trap = new Trap(TRAP.END_PARAGRAPH, makeLiteral(parrafo.getName()));
//JGG4            injector.setPrevTrap(trap);
//JGG4        }

        if (!firstP) {
        	parrafo = module.addParagraph(name, s.line, numStatements);
        	grafo = newGraph(parrafo.getOrden(), parrafo.getName());
        }
        
//JGG4        trap = new Trap(TRAP.IN_PARR, makeLiteral(parrafo.getName()), parrafo.getIndex());
        trap = new Trap(TRAP.IN_PARR, parrafo.getIndex());
        injector.setNextTrap(trap);
        
        newBlock(last, true, TRAP.IN_PARR);

        injector.loadTraps(s.line, s.column);
 
        return s;
   }

	public StmtCobol processPerform(StmtCobol stmt, StmtCobol last) {

		if (isInLine(stmt)) {
			performInline(stmt);
		}
		else {
			performOutline(stmt);
		}	
		
		calculatePerformComplexity(stmt);
		return stmt;
	}

	/**
	 * El PERFORM es inline si:
	 *    A: Es de la forma PERFORM 
	 *    B: No es de la forma  PERFORM ID .....
	 *    C: Nota: PERFORM ID TIMES es convertido a PERFORM TIMES ID
	 * @param stmt La sentencia PERFORM a analizar
	 * @return cierto si el perform es inline
	 */
	private boolean isInLine(StmtCobol stmt) {
		if (stmt.hasOptions() == false) return true;
		Option opt = stmt.getOptionByPos(0);
		if (opt.getId() != SYM) return true;
		return false;
	}	

	private void performInline(StmtCobol stmt) {
	    SDPSymbol verb = stmt.getVerb();
	    
		injector.loadTraps(verb.line, verb.column);
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStatements);
		newBlock(stmt, true, TRAP.INLINE);
		skipBlock = true;
		addToGraph(stmt, "INLINE");
	}

	private void performOutline(StmtCobol stmt) {
		SDPSymbol perform = stmt.getVerb();
		String from    = stmt.getOptionByPos(0).getName();
		String to      = null;
		
        injector.setPrevTrap(new Trap(TRAP.BEG_PERFORM, makeLiteral(from)));
		injector.loadTraps(perform.line, perform.column);
        injector.setNextTrap(new Trap(TRAP.END_PERFORM, makeLiteral(from)));
        
        Option thru = stmt.getOption(THRU);
        if (thru != null) {
        	to = thru.getParm(0).value;
        }
        module.addParagraphReference(from, to);
                
        //JGG Mirar esto. Caso procedure perform
        if (currBlock != null) currBlock.addPerform(from);
        
        boolean loop = stmt.getOption(UNTIL) == null ? false : true;
		grafo.addLinearNode(TRAP.PERFORM, TRAP.PERFORM, from, to, numStatements, loop);
	}
	
	private void addToGraph(StmtCobol stmt, String from) {
		boolean loop = false;
        if (stmt.getOptionByName("UNTIL") != null   ||
            stmt.getOptionByName("VARYING") != null) {
        	loop = true;
        }
//        grafo.addBlock(NodeTypes.PERFORM, from, loop);
	}
	
	private void calculatePerformComplexity(StmtCobol stmt) {
		for (Option opt : stmt.getOptions()) {
			switch (opt.getId()) {
			   case TIMES:
			   case UNTIL:
			   case AND:
			   case OR:	   
				   parrafo.incCiclomatic();
				   break;
			}
		}
	}
	
    public StmtCobol processIf(StmtCobol stmt) {
        parrafo.incCiclomatic();
        stackFlujo.push(stmt);

        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStatements);
        newBlock(stmt, true, TRAP.IF); 
        skipBlock = true;

        return stmt;

    }

    public StmtCobol processElse(StmtCobol stmt) {
        newBlock(stmt, true, TRAP.ELSE);
        return stmt;
    }

    public StmtCobol processEvaluate(StmtCobol stmt) {
        grafo.addBlock(TRAP.SEARCH, numStatements, stmt.toText());
        stackFlujo.push(stmt);
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStatements);
        return stmt;
    }

    public StmtCobol processSearch(StmtCobol stmt) {
        grafo.addBlock(TRAP.SEARCH, numStatements, stmt.toText());
        stackFlujo.push(stmt);
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStatements);
        return stmt;
    }
    
    public StmtCobol processWhen(StmtCobol stmt) {   	
    	stackFlujo.peek().addOption(stmt.asOption());
        parrafo.incCiclomatic();
        newBlock(stmt, true, TRAP.WHEN);
        return stmt;
    }
    

	/**
	 * Chequea las construcciones de la forma: VERBO imperative-statement
	 * Si imperative-statement es un PERFORM PARRAFO 
	 * es necesario incluirlo dentro de un bloque:<br>
	 *     PERFORM<br>
	 *        PERFORM PARRAFO<br>
	 *     END-PERFORM<br>
	 *        
	 * @param stmt La sentencia a analizar
	 * @return la sentencia a analizar
	 */
	public StmtCobol wrapPerform(StmtCobol stmt) {
		parrafo.incCiclomatic();
		SDPSymbol s = stmt.getVerb();
		if (s.sym != NodeTypes.PERFORM) return stmt;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM, s.line));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM, s.line));
		return stmt;
	}
	
	public StmtCobol fileAction(int action, SDPSymbol base, Tokens files) {
        StmtCobol stmt = new StmtCobol(base, numStatements);
        return stmt;
        /*JGG
        stmt.addTokens(files);
        for (SDPSymbol s : files.getTokens()) {
             Persistence f = module.getFile( (String) s.value);
             injector.setPrevTrap(new Trap(action, f.getPos() + 1));
        }
        return stmt;
        */
	}
	
	public StmtCobol fileAccess(int action, SDPSymbol base, SDPSymbol f) {
        Persistence p;
        
		StmtCobol stmt = new StmtCobol(base, numStatements);
/*JGG
		String name = getName(f);
        stmt.addSymbol(f);


        switch (action) {
           case TRAP.ACC_READ:
           case TRAP.ACC_DELETE:	   
        	    p = module.getFile(name);
        	    break;
           default: 	    
        	   p = module.getFileByRecord(name);
        }
        		                                  
        injector.setPrevTrap(new Trap(action, p.getPos() + 1));
*/        
        return stmt;
	}     

	/**
     * Procesa una sentencia EXIT xxxx Insertando el trap de fin correspondiente
     * Si EXIT no lleva etiqueta, debe ser la unica sentencia en el parrafo .
     *
     * @param stmt
     *            StmtCobol EXIT
     * @return la misma StmtCobol
     */
	public StmtCobol processExit(StmtCobol stmt) {
//		if (stmt.getSymbols().size() == 0) return stmt;
        injector.removeTraps();
        parrafo.setExit(true);
		return stmt;
	}

    public StmtCobol processExitParrafo(StmtCobol stmt) {
//JGG4        injector.setPrevTrap(new Trap(TRAP.END_PARAGRAPH, parrafo.getName()));
        return stmt;
    }
	
	public StmtCobol processCall (SDPSymbol verbo, SDPSymbol rutina) {
		int callMode = CDG.CALL_CALL;
		int callType = CDG.CALL_DYNAMIC;
		
        StmtCobol stmt = new StmtCobol(verbo);
        stmt.addRValue(rutina);

	    String nombre = (String) rutina.value;
	    if (rutina.sym == LITERAL) {
	    	nombre = makeLiteral(nombre, true);
	    	callType = CDG.CALL_STATIC;
	    }

	    module.getSummary().setCallMode(callMode);
	    module.getSummary().setCallType(callType);
	    
        injector.setPrevTrap(new Trap(TRAP.BEG_CALL, nombre));
        injector.setNextTrap(new Trap(TRAP.END_CALL, nombre));
        
        processDependence((String) rutina.value, rutina.sym);
        
        grafo.addLinearNode(TRAP.CALL, callType, (String) rutina.value, numStatements);
        
        return stmt;
	}

	private void processDependence(String nombre, Integer tipo) {
		int modo = (tipo == LITERAL) ? CDG.CALL_STATIC : CDG.CALL_DYNAMIC;
    	
		Routine rut = new Routine();
    	rut.setNombre(nombre.trim());
    	rut.setMetodo(CDG.CALL_CALL);
    	rut.setModo(modo);
    	rut.setEstado(CDG.DEP_ST_DECLARED);
        module.addRoutine(rut);
	}
	
	public void endProgram(StmtCobol endProgram) {
        Trap trap = new Trap(TRAP.END_MODULE, makeLiteral(module.getName()));
        trap.setEndStatement(true);
        trap = injector.setPrevTrap(trap);
        injector.loadTraps(endProgram.getBegLine(), 0);
        
        Sections sections = module.getSections();
        Integer sect = info.removeSection();
        while (sect != null) {
        	Integer line = info.removeSectionLine();
            sections.setSectionOrDivision(sect, line);
            sect = info.removeSection();
        }
//        grafo.endCycle(NodeTypes.BEGIN);
        
//        hasEndProgram = true;
	}

	public void EndOfFile(StmtCobol last, boolean hasEndCode) {
		trapEOF(last, hasEndCode);
        module.setStatements(numStatements);
        module.setParagraphReferences();
        grafo.endGraph(numStatements);
//        insertSDPFields();  // Debe ser lo ultimo
//        setInjectorVars();
	}
	
	/**
     * Realiza el proceso de fin de fichero Controlando si la ultima sentencia
     * ejecutada en una que impide que se ejecute y si estamos dentro de un
     * parrafo o en linea.
     *
     * @param last
     *            La ultima sentencia analizada
     * @param hasEndCode
     *            Indicador de si existe sentencias de fin de programa:<br>
     *            STOP RUN, GOBACK, ....
     */
	public  void trapEOF(StmtCobol last, boolean hasEndCode) {
	    module.lastParagraph(last.getEndLine(), numStatements);
        module.lastBlock(last.getEndLine(), numStatements);
//JGG4	    if ( !parrafo.isExit() && !parrafo.isVirtual() && hasEndProgram == false) {
//JGG4	    	injector.setPrevTrap(new Trap(TRAP.END_PARAGRAPH, makeLiteral(parrafo.getName())));
//JGG4	    }

	    if (hasEndCode == false) {
	    	Trap trap = new Trap();
            trap.setEndStatement(true);
	    	trap.load(TRAP.END_MODULE, makeLiteral(module.getName()));
            injector.setPrevTrap(trap);
        }

	    injector.loadTraps(Integer.MAX_VALUE, 0);
   }

	public void incComplexity() {
		parrafo.incCiclomatic();
	}
	
	/**
	 * Verifica la construcion END-IF o END-IF. o .<br>
     *
	 * Cuando encuentra un punto se debe verificar si esta en un sentencia de bifurcacion
	 * es decir, IF, EVALUATE, ....
	 * Esta situacion se da si la pila stackFlujo tiene informacion
	 * En ese caso se cierran todos los flujos abiertos
	 *  
	 * @param stmt Sentencia actual
	 * @param last Ultima sentencia procesada 
	 * @return La sentencia actual
	 */
	public StmtCobol checkFlujo(StmtCobol stmt, StmtCobol last) {
	    Trap trap = null;
	    Integer cause = null;
	
	    stmt.setEndPoint(true);
	    last.setEndPoint(true);
	    
	    if (stackFlujo.isEmpty()) return stmt;
	    StmtCobol flujo = stackFlujo.pop();

	    while (flujo != null) {
	    	cause = null;
	        switch (flujo.getVerb().sym) {
	           case IF:        cause = TRAP.END_IF;     break;
               case EVALUATE:  cause = TRAP.END_EVAL;   break;
               case SEARCH:    cause = TRAP.END_SEARCH; break;
	        }
	        if (cause != null) trap = new Trap(cause, bloques + 1);
            injector.setPrevTrap(trap);
	        module.endBlock(stmt.getBegLine(), numStatements);
	        newBlock(stmt, false, cause);
	        flujo = (stackFlujo.isEmpty()) ? null : stackFlujo.pop();
	    }
	    return stmt;
	}

	public StmtCobol endVerb(SDPSymbol s) {
		StmtCobol stmt = new StmtCobol(s);
		String tipo = s.value.substring(4).toUpperCase();

		if (tipo.compareTo("IF")       == 0) return endIf(stmt);
		if (tipo.compareTo("SEARCH")   == 0) return endSearch(stmt);
		if (tipo.compareTo("PERFORM")  == 0) return endPerform(stmt);
		if (tipo.compareTo("EVALUATE") == 0) return endPerform(stmt);

        return stmt;
	}
	
	public StmtCobol endIf(StmtCobol stmt) {
		cleanStackFlujo("IF");
        newBlock(stmt, true, TRAP.END_IF);

        return stmt;
	}
	
    public StmtCobol endEvaluate(StmtCobol stmt) {
        cleanStackFlujo("EVALUATE");
        newBlock(true, TRAP.END_EVAL);
        return stmt;
    }

    public StmtCobol endSearch(StmtCobol stmt) {
        cleanStackFlujo("SEARCH");
        newBlock(true, TRAP.END_SEARCH);
        grafo.endBlock(numStatements, false);
        return stmt;
    }

    public StmtCobol endPerform(StmtCobol stmt) {
        newBlock(stmt, true, TRAP.END_PERFORM);
        //grafo.endCycle(NodeTypes.PERFORM);
        return stmt;
    }
    
    private void cleanStackFlujo(String txt) {
        while (stackFlujo.pop().getVerbName().compareToIgnoreCase(txt) != 0);
    }

        
    public void trapEndModule() {
		injector.setPrevTrap(new Trap(TRAP.END_MODULE, makeLiteral(module.getName())));
		//grafo.endCycle(NodeTypes.BEGIN);
	}
	

    private String getName(SDPSymbol s) {
		return ((String) s.value).trim();
	}
	
    
	private String makeLiteral(String txt) {
        return makeLiteral(txt, false);
	}
	
	private String makeLiteral(String txt, boolean trim) {
		String aux = (trim) ? txt.trim() : txt;
		return '\'' + aux + '\'';		
	}
	
	private void setInjectorVars() {
		String s_grp = "S" + (System.currentTimeMillis() / 100000);
		module.setSDPGroup(s_grp);
        injector.setVar("SDP-GROUP", s_grp);
        injector.setVar("MODULE"   , module.getName());
        injector.setVar("BLOCKS"   , module.getNumBloques());
        injector.setVar("PARRS"    , module.getNumParagraphs() + 1);
        injector.setVar("FILES"    , module.getNumFiles() + 1);
        injector.setVar("NPARRS"   , module.getNumParagraphs());
        injector.setVar("NFILES"   , module.getNumFiles());
//        injector.setVar("ID1"      , module.getIdModule().substring( 0, 16)); 
//    	injector.setVar("ID2"      , module.getIdModule().substring(16, 32));

    	setDefaultFlags();
	}
	
	private void setDefaultFlags() {
		char[] flags = new char[16];
		for (int idx = 0 ; idx < 16; idx++) flags[idx] = '0';
		
		if (cfg.getBoolean(CFG.PRF_VERIFY,    false)) flags[TRAP.FLG_VERIFY]    = '1';
		if (cfg.getBoolean(CFG.PRF_PROFILE,   false)) flags[TRAP.FLG_PROFILE]   = '1';
		if (cfg.getBoolean(CFG.PRF_SUSPEND,   false)) flags[TRAP.FLG_PROFILE]   = '1';
		if (cfg.getBoolean(CFG.PRF_MODULE,    false)) flags[TRAP.FLG_MODULE]    = '1';
		if (cfg.getBoolean(CFG.PRF_PARAGRAPH, false)) flags[TRAP.FLG_PARAGRAPH] = '1';		
		if (cfg.getBoolean(CFG.PRF_PERFORM,   false)) flags[TRAP.FLG_PERFORM]   = '1';
		if (cfg.getBoolean(CFG.PRF_CALL,      false)) flags[TRAP.FLG_CALL]      = '1';
		if (cfg.getBoolean(CFG.PRF_SQL,       false)) flags[TRAP.FLG_SQL]       = '1';
		if (cfg.getBoolean(CFG.PRF_CICS,      false)) flags[TRAP.FLG_CICS]      = '1';

		injector.setVar("FLAGS", String.valueOf(flags));
		injector.setVar("DEPTH", cfg.getInteger(CFG.PRF_DEPTH, 9999));

	}
	
	/**
	 * Examina donde deben inyectarse las variables de SDP 
	 *
	 * Si existe Working, en la siguiente linea
	 * Si no existe, antes de la siguiente seccion
	 * En ese caso hay que verificar si existe DATA DIVISION 
	 * y si no insertarla tambin
	 * 
	 */

	public void insertSDPFields() {
		Sections sections = module.getSections();
		
		int line = 0;
		
		Trap trap = new Trap(TRAP.WORKING);

		if (!sections.hasWorkingStorage()) {
			trap.addLine(0, "       WORKING-STORAGE SECTION.");
		}
		if (!sections.hasDataDivision()) {
			trap.addLine(0, "       DATA DIVISION.");
		}
		
		if (sections.hasWorkingStorage()) {
			line = sections.getWorkingStorage() + 1;
		} else if (sections.hasLocalStorage()) {
			line = sections.getLocalStorage();
		} else if (sections.hasLinkage()) {
			line = sections.getLinkage();
		} else {
			line = sections.getProcedure();
		}
		
		trap.setLine(line);
		injector.setTrap(0, trap);
	}

	public void notSupportedSection(SDPSymbol s) {
	    notSupported(MSG.SUPPORT_SECTION, s.line + 1, s.column + 1, (String) s.value);
	}
	
	public void notSupportedParrName(SDPSymbol s) {
	    notSupported(MSG.SUPPORT_NAME, s.line + 1, s.column + 1, (String) s.value);
	}
	
	public void notSupportedCopy(SDPSymbol s) {
	    notSupported(MSG.SUPPORT_COPY, s.line + 1, s.column + 1, (String) s.value);
	}
	private void notSupported(int code, Object... parms) {
	     throw new NotSupportedException(code, module.getName(), parms);
	}

	
	public Variable addVar(SDPSymbol level, SDPSymbol name) {
		Variable var = new Variable((String) level.value, (String) name.value);
		var.setLine(level.line);
		var.setColumn(level.column);
//		var.setSection(section);
		module.addVariable(var);
		return var;
	}

	public SDPSymbol setVarRead(SDPSymbol s) {
		/*JGG
		if (s.sym == FUNCTION) return s;
		String name = (String) s.value;
		module.setVarRead(name, s.getParentName());
		*/
		return s;
	}

	public SDPSymbol setVarWrite(SDPSymbol s) {
		return s;
		/*JGG
		String name = (String) s.value;
		module.setVarWrite(name);
		return s;
		*/
	}
	
	public Variable setVarWrite(Variable v) {
		if (v == null) return v;
		if (v.getParent() == null) return v;
		module.setVarWrite(v.getName(), v.getParent().getName());
		return v;
	}

	public SDPSymbol setVarListWrite(SDPSymbol lista) {
		module.setVarWrite(lista.value, lista.getParentName());
		for (SDPSymbol s : lista.getSymbols()) {
			module.setVarWrite(s.value, s.getParentName());
		}
		return lista;
	}
	
	public void checkCall(SDPSymbol l, SDPSymbol r) {
		boolean isVar = false;
		
		if (r == null) return;
		
		switch (r.getId()) {
            case LITERAL: 
		    case ZCZSym.FIGURATIVE: isVar = false; break;
		    case ID:                isVar = true;  break;
		    default:                return;
		}
		
        for (SDPSymbol s : l.getSymbols()) {
			   module.setVarValue(s, isVar);
		}
	}

	private SubGraph newGraph(int id, String name) {
		SubGraph sub = graphs.createGraph(id, name);
		rootGraph.add(sub);
		return sub;
	}
	
}
