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

import java.util.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.InjectorSingleton;
import com.jgg.sdp.parser.info.*;
import com.jgg.sdp.parser.tools.*;
import com.jgg.sdp.parser.work.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.grafo.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.parser.lang.ZCCSym;
import com.jgg.sdp.parser.lang.ZCZSym;

public class ZCCCode extends ZCZCode {

	private Injector injector = InjectorSingleton.getInjector();
	
	private Grafo    grafo;
	
	private Paragraph parrafo = null;

	private Block currBlock = null;
	
	// Pila de controles de flujo: IF, PERFORM, EVALUATE
    private Stack<Statement> stackFlujo = new Stack<Statement>(); 

    private final ResourceBundle badStmt = ResourceBundle.getBundle(SYS.BAD_STMTS);
    
	private ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

    // Flag para controlar sentencias que admiten imperative-statement
    // Ejemplo:
    //   READ DATO AT END imperative-statement
    private boolean imperative = false;
    private int     numStmts      = 0;
    private int     bloques       = 0;
    private boolean skipBlock     = false;
//    private boolean hasEndProgram = false;
    
    public ZCCCode(Module module) {
       super(module);
	   grafo    = module.getGrafo();
	}

    public int  getStmts()      { return numStmts;   }
	
	public void incStmt()       { numStmts++;        }
    
    public void setImperative() { imperative = true; }

    public Variable getVariable(Symbol s) {
    	return null;
    	/*JGG
    	String name = (String) s.value;
    	Variable var = module.getVariable(name);
    	if (var == null) module.setVarMissing();
        return var;
        */	
    }
    
    public Statement setAtEnd(Statement stmt) { 
        parrafo.incCiclomatic();
        newBlock(stmt, true, TRAP.ATEND);
        return stmt; 
    }    

	public void beginCode(Symbol s) {
		info.addDivision(CDG.SECT_PRC, s.left);
        Trap trap = new Trap(TRAP.BEG_MODULE, makeLiteral(module.getName()));
        trap = injector.setPrevTrap(trap);
	}
	
	public Symbol makeSymbol(int code, int line, int column, String text) {
         Symbol s = new Symbol(code, line, column, text);
		 return symbolFactory.newSymbol(text, code, s);
	}

	public Symbol makeSymbol(Symbol s, int code, String text) {
        Symbol ss = new Symbol(code, s.left, s.right, text);
        return symbolFactory.newSymbol(text, code, ss);
	}
	
	/**
     * Al inicio del proceso se crea un parrafo virtual Este parrafo se aplica
     * si el programa no se inicia en un parrafo y se elimina si se inicia con
     * un parrafo.
     *
     * @param endp
     *            Punto de PROCEDURE DIVISION
     */
	public void   parrafoVirtual(Symbol endp) {
        parrafo = module.addParagraph("", endp.left, 0);		
	}

	public int newBlock(boolean trap, Integer cause) {
		return newBlock(null, trap, cause);
	}
	
	public int newBlock(Statement stmt, boolean genTrap, Integer cause) {
        int bloque = ++bloques;
        int endLine = -1;
		int stmts   = numStmts;
		
		if (stmt != null) {
		    endLine = stmt.getEndLine();
		    stmts++;
		}
		
        currBlock = new Block(bloque, stmts, cause);
		
        module.addBlock(currBlock, endLine);
        grafo.addNode(new Nodo(currBlock, cause));
        
        if (genTrap) {
		   Trap trap = new Trap(TRAP.BLOCK, bloque);
	       injector.setNextTrap(trap);
        }   
        
		return bloque;		
	}

	public Statement processStatement(Statement stmt, Statement last) {

		checkBadStmt(stmt);
		
		if (imperative) checkPerform(stmt);
		
		// Es la primera sentencia 
		if (last == null && parrafo.isVirtual()) {  // Hay parrafo incial?
		    numStmts--; // Ya se ha contando la instruccion
		    int bloque = newBlock(null, false, TRAP.BEG_MODULE);
		    currBlock.setBegin(stmt.getBegLine());
		    numStmts++;
		    injector.setPrevTrap(new Trap(TRAP.BLOCK, bloque));
		}
		
		if (currBlock.getBegin() == -1 && !skipBlock) {
		    module.beginBlock(stmt.getBegLine());
		}
		
		skipBlock = false;
		
		injector.loadTraps(stmt.getBegLine(), stmt.getBegColumn());

		imperative = false;
		return stmt;
	}
	
	public void checkPerform(Statement stmt) {
		parrafo.incCiclomatic();
		if (stmt.getVerb().sym != ZCCSym.PERFORM) return;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM));
	}
	
	public Symbol processParrafo(Symbol s, Statement last) {
	    boolean firstP = false;
        Trap trap = null;
	    String name = getName(s);
	    
	    if (name.length() > SYS.MAX_PARR_NAME) notSupportedParrName(s);
	    
        // Si es el primer parrafo y no hay sentencias
        // Reemplazar el parrafo virtual
        // Incluir una llamada a ese Perform
	    
        if (numStmts == 0) {
            parrafo = module.getParagraph("");
            if (parrafo != null) {
                parrafo.setName(name);
                parrafo.setLine(s.left);
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

        if (!firstP) parrafo = module.addParagraph(name, s.left, numStmts);
        
//JGG4        trap = new Trap(TRAP.IN_PARR, makeLiteral(parrafo.getName()), parrafo.getIndex());
        trap = new Trap(TRAP.IN_PARR, parrafo.getIndex());
        injector.setNextTrap(trap);
        
        newBlock(last, true, TRAP.IN_PARR);

        injector.loadTraps(s.left, s.right);
 
        return s;
   }

	public Statement processPerform(Statement stmt, Statement last) {

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
	private boolean isInLine(Statement stmt) {
		if (stmt.hasOptions() == false) return true;
		Option opt = stmt.getOptionByPos(0);
		if (opt.getId() != ZCCSym.ID) return true;
		return false;
	}	

	private void performInline(Statement stmt) {
	    Symbol verb = stmt.getVerb();
	    
		injector.loadTraps(verb.left, verb.right);
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStmts + 1);
		newBlock(stmt, true, TRAP.INLINE);
		skipBlock = true;
	}

	private void performOutline(Statement stmt) {
		Symbol perform = stmt.getVerb();
		String from    = stmt.getOptionByPos(0).getName();
		String to      = null;
		
        injector.setPrevTrap(new Trap(TRAP.BEG_PERFORM, makeLiteral(from)));
		injector.loadTraps(perform.left, perform.right);
        injector.setNextTrap(new Trap(TRAP.END_PERFORM, makeLiteral(from)));
        
        Option thru = stmt.getOption(ZCCSym.THRU);
        if (thru != null) {
        	to = thru.getVar(0).getName();
        }
        module.addParagraphReference(from, to);
        
        //JGG Mirar esto. Caso procedure perform
        if (currBlock != null) currBlock.addPerform(from);
        
	}
	
	private void calculatePerformComplexity(Statement stmt) {
		for (Option opt : stmt.getOptions()) {
			switch (opt.getId()) {
			   case ZCCSym.TIMES:
			   case ZCCSym.UNTIL:
			   case ZCCSym.AND:
			   case ZCCSym.OR:	   
				   parrafo.incCiclomatic();
				   break;
			}
		}
	}
	
    public Statement processIf(Statement stmt) {
        parrafo.incCiclomatic();
        stackFlujo.push(stmt);
        return stmt;
        /*
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStmts + 1);
        newBlock(stmt, true, TRAP.IF); 
        skipBlock = true;

        return stmt;
        */
    }

    public Statement processEvaluate(Statement stmt) {
        stackFlujo.push(stmt);
        module.closeBlock(stmt.getBegLine(), stmt.getEndLine(), numStmts);
//        newBlock(true, TRAP.EVALUATE);
//        skipBlock = true;
        return stmt;
    }
    	
    public Statement processWhen(Statement stmt) {
        parrafo.incCiclomatic();
        newBlock(stmt, true, TRAP.WHEN);
        return stmt;
    }
    
    public Statement processElse(Statement stmt) {
        newBlock(stmt, true, TRAP.ELSE);
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
	public Statement wrapPerform(Statement stmt) {
		parrafo.incCiclomatic();
		Symbol s = stmt.getVerb();
		if (s.sym != ZCCSym.PERFORM) return stmt;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM, s.left));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM, s.left));
		return stmt;
	}
	
	public Statement fileAction(int action, Symbol base, Tokens files) {
        Statement stmt = new Statement(base, numStmts);
        return stmt;
        /*JGG
        stmt.addTokens(files);
        for (Symbol s : files.getTokens()) {
             Persistence f = module.getFile( (String) s.value);
             injector.setPrevTrap(new Trap(action, f.getPos() + 1));
        }
        return stmt;
        */
	}
	
	public Statement fileAccess(int action, Symbol base, Symbol f) {
        Persistence p;
        
		Statement stmt = new Statement(base, numStmts);
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
     *            Statement EXIT
     * @return la misma Statement
     */
	public Statement processExit(Statement stmt) {
		if (stmt.getSymbols().size() == 0) return stmt;
        injector.removeTraps();
        parrafo.setExit(true);
		return stmt;
	}

    public Statement processExitParrafo(Statement stmt) {
//JGG4        injector.setPrevTrap(new Trap(TRAP.END_PARAGRAPH, parrafo.getName()));
        return stmt;
    }
	
	public Statement processCall (Symbol verbo, Symbol rutina) {
		int callMode = CDG.CALL_CALL;
		int callType = CDG.CALL_DYNAMIC;
		
        Statement stmt = new Statement(verbo);
        stmt.addSymbol(rutina);

	    String nombre = (String) rutina.value;
	    if (rutina.sym == ZCCSym.LITERAL) {
	    	nombre = makeLiteral(nombre, true);
	    	callType = CDG.CALL_STATIC;
	    }

	    module.getSummary().setCallMode(callMode);
	    module.getSummary().setCallType(callType);
	    
        injector.setPrevTrap(new Trap(TRAP.BEG_CALL, nombre));
        injector.setNextTrap(new Trap(TRAP.END_CALL, nombre));
        
        processDependence((String) rutina.value, rutina.sym);
        return stmt;
	}

	private void processDependence(String nombre, Integer tipo) {
		int modo = (tipo == ZCCSym.LITERAL) ? CDG.CALL_STATIC : CDG.CALL_DYNAMIC;
    	
		Routine rut = new Routine();
    	rut.setNombre(nombre.trim());
    	rut.setMetodo(CDG.CALL_CALL);
    	rut.setModo(modo);
    	rut.setEstado(CDG.DEP_ST_DECLARED);
        module.addRoutine(rut);
	}
	
	public void endProgram(Statement endProgram) {
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
//        hasEndProgram = true;
	}

	public void EndOfFile(Statement last, boolean hasEndCode) {
		trapEOF(last, hasEndCode);
        module.setStatements(numStmts);
        module.setParagraphReferences();
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
	public  void trapEOF(Statement last, boolean hasEndCode) {
	    module.lastParagraph(last.getEndLine(), numStmts);
        module.lastBlock(last.getEndLine(), numStmts);
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
	public Statement checkFlujo(Statement stmt, Statement last) {
	    Trap trap = null;
	    Integer cause = null;
	
	    if (stackFlujo.isEmpty()) return stmt;
	    Statement flujo = stackFlujo.pop();

	    while (flujo != null) {
	        switch (flujo.getVerb().sym) {
	           case ZCCSym.IF: 
	               cause = TRAP.END_IF;
	               trap = new Trap(cause, bloques + 1);
	               injector.setPrevTrap(trap);
	               break;
               case ZCCSym.EVALUATE:
                   cause = TRAP.END_EVAL;
                   trap = new Trap(cause, bloques + 1);
                   injector.setPrevTrap(trap);
                   break;
	        }
	        module.endBlock(stmt.getBegLine(), numStmts);
	        newBlock(null, false, cause);
	        flujo = (stackFlujo.isEmpty()) ? null : stackFlujo.pop();
	    }
	    return stmt;
	}
	
	public Statement endIf(Statement stmt) {
	    stackFlujo.pop();
        newBlock(stmt, true, TRAP.END_IF);
        return stmt;
	}
	
    public Statement endPerform(Statement stmt) {
        newBlock(stmt, true, TRAP.END_PERFORM);
        return stmt;
    }
    
    public Statement endEvaluate(Statement stmt) {
        stackFlujo.pop();
        newBlock(true, TRAP.END_EVAL);
        return stmt;
    }

    public void trapEndModule() {
		injector.setPrevTrap(new Trap(TRAP.END_MODULE, makeLiteral(module.getName())));
	}
	

    private String getName(Symbol s) {
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

	/**
	 * Verifica que la sentencia no este en la lista de no permitidas
	 * Si lo esta la inserta en la lista
	 * @param stmt
	 */
	private void checkBadStmt(Statement stmt) {
	    String verb = stmt.getVerbName();
	    if (badStmt.containsKey(verb)) {
	        module.addBadSentence(verb,
	                              stmt.getBegLine(), 
	                              stmt.getEndLine(), 
	                              stmt.getBegColumn());
	    }
	}
	
	public void notSupportedSection(Symbol s) {
	    notSupported(MSG.SUPPORT_SECTION, s.left + 1, s.right + 1, (String) s.value);
	}
	
	public void notSupportedParrName(Symbol s) {
	    notSupported(MSG.SUPPORT_NAME, s.left + 1, s.right + 1, (String) s.value);
	}
	
	public void notSupportedCopy(Symbol s) {
	    notSupported(MSG.SUPPORT_COPY, s.left + 1, s.right + 1, (String) s.value);
	}
	private void notSupported(int code, Object... parms) {
	     throw new NotSupportedException(code, module.getName(), parms);
	}

/*	
	public Variable addVar(Symbol level, Symbol name) {
		Variable var = new Variable((String) level.value, (String) name.value);
		var.setLine(level.left);
		var.setColumn(level.right);
		var.setSection(section);
		module.addVariable(var);
		return var;
	}
*/
	public Symbol setVarRead(SymbolExt s) {
		if (s.sym == ZCCSym.FUNCTION) return s;
		String name = (String) s.value;
		module.setVarRead(name, s.getParentName());
		return s;
	}

	public Symbol setVarWrite(SymbolExt s) {
		String name = (String) s.value;
		module.setVarWrite(name);
		return s;
	}
	
	public Variable setVarWrite(Variable v) {
		if (v == null) return v;
		if (v.getParent() == null) return v;
		module.setVarWrite(v.getName(), v.getParent().getName());
		return v;
	}

	public SymbolExtList setVarListWrite(SymbolExtList lista) {
		for (SymbolExt s : lista.getVarList()) {
			module.setVarWrite(s.getName(), s.getParentName());
		}
		return lista;
	}
	
	public void checkCall(SymbolExtList l, SymbolExt r) {
		String value = null;
		boolean id = false;
		
		if (r == null) return;
		
		switch (r.getId()) {
		     case ZCCSym.ID: 
		    	  id = true;
		    	  value = (String) r.value;
		    	  break;
		     case ZCCSym.LITERAL:
		    	  id = false;
		    	  value = (String) r.value;
		    	  break;
		     case ZCZSym.FIGURATIVE:
		    	  id = false;
		    	  value = "";
		    	  break;
		    default:
		    	 return;
		}
		
        for (SymbolExt s : l.getVarList()) {
			   module.setVarValue(s.getName(), value, id);
		}
	}
	
}
