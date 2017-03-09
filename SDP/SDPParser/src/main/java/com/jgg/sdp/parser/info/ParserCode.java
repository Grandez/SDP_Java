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
package com.jgg.sdp.parser.info;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.core.ctes.TRAP;
import com.jgg.sdp.core.exceptions.NotSupportedException;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.grafo.Grafo;
import com.jgg.sdp.module.grafo.Nodo;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.tools.*;
import com.jgg.sdp.parser.lang.OCSym;

public class ParserCode {

	private Module   module;
	private Injector injector;
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
    
public ParserCode(Module module) {
	   this.module = module;
	   injector = module.getInjector();
	   grafo    = module.getGrafo();
	}

    public int  getStmts()      { return numStmts;   }
	
	public void incStmt()       { numStmts++;        }
    
    public void setImperative() { imperative = true; }

    public Statement setAtEnd(Statement stmt) { 
        parrafo.incCiclomatic();
        newBlock(stmt, true, TRAP.ATEND);
        return stmt; 
    }    

    /**
     * Agrupa las acciones a realizar al iniciar el codigo.
     *
     * @param s El simbolo inicial
     */
	public void beginCode(Symbol s) {
		module.getSections().setProcedure(s.left); 
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
	
	/**
     * genTrap indica si se debe generar trap o no .
     *
     * @param stmt La sentencia en proceso
     * @param genTrap 
     *            Generar Trap o no
     * @param cause
     *            Constante indicando el motivo del bloque
     * @return el numero de bloque
     */
	public int newBlock(Statement stmt, boolean genTrap, Integer cause) {
        int bloque = ++bloques;
        int endLine = -1;
		int stmts   = numStmts;
		
		if (stmt != null) {
		    endLine = stmt.getLastLine();
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
	
	/**
     * Realiza las acciones que hay que realizar en cada sentencia<br>
     * Por cada una de ellas hay que:<br>
     * <li>
     * <ul>
     * Actualizar el inicio del bloque si no se ha hecho ya
     * </ul>
     * <ul>
     * Cargar los traps que deben ir antes de la sentencia
     * </ul>
     * </li>.
     *
     * @param stmt
     *            la sentencia a procesar
     * @param last
     *            the last
     * @return La sentencia procesada
     */
	public Statement processStatement(Statement stmt, Statement last) {
		if (stmt.getVerb().sym == OCSym.CHECK) return stmt;

		checkBadStmt(stmt);
		
		if (imperative) checkPerform(stmt);
		
		// Es la primera sentencia 
		if (last == null && parrafo.isVirtual()) {  // Hay parrafo incial?
		    numStmts--; // Ya se ha contando la instruccion
		    int bloque = newBlock(null, false, TRAP.BEG_MODULE);
		    currBlock.setBegin(stmt.getFirstLine());
		    numStmts++;
		    injector.setPrevTrap(new Trap(TRAP.BLOCK, bloque));
		}
		
		if (currBlock.getBegin() == -1 && !skipBlock) {
		    module.beginBlock(stmt.getFirstLine());
		}
		
		skipBlock = false;
		
		injector.loadTraps(stmt.getFirstLine(), stmt.getFirstColumn());

		imperative = false;
		return stmt;
	}
	
	public void checkPerform(Statement stmt) {
		parrafo.incCiclomatic();
		if (stmt.getVerb().sym != OCSym.PERFORM) return;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM));
	}
	
	/**
     * Procesa un parrafo.
     *
     * @param s
     *            Simbolo con el nombre del parrafo
     * @param last
     *            Ultima sentencia procesada para control de bloques
     * @return El Simbolo
     */
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
            parrafo.setName(name);
            parrafo.setLine(s.left);
            parrafo.incReferences();
            trap = new Trap(TRAP.FIRST_PARR, name, module.getName());
            injector.setPrevTrap(trap);
            firstP = true;
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

	/**
     * Proceso para el tratamiento de PERFORM parrafo1 .... Se ejecuta antes de
     * processStatement
     *
     * @param perform
     *            El symbol PERFORM
     * @param tokens
     *            Los tokens asociados al PERFORM
     * @param last
     *            Ultima sentencia procesada
     * @return La sentencia asociada
     */

	public Statement processPerform(Symbol perform, Tokens tokens, Statement last) {
		Statement stmt = new Statement(perform);
	    
		stmt.addTokens(tokens);
	    
		if (isInLine(stmt)) {
			injector.loadTraps(perform.left, perform.right);
		    module.closeBlock(stmt.getFirstLine(), stmt.getLastLine(), numStmts + 1);
			newBlock(stmt, true, TRAP.INLINE);
			skipBlock = true;
		}
		else {
			performOutline(stmt);
		}	
		
		calculatePerformComplexity(stmt);
		return stmt;
	}

    public Statement processIf(Statement stmt) {
        parrafo.incCiclomatic();
        stackFlujo.push(stmt);
        module.closeBlock(stmt.getFirstLine(), stmt.getLastLine(), numStmts + 1);
        newBlock(stmt, true, TRAP.IF); 
        skipBlock = true;

        return stmt;
    }

    public Statement processEvaluate(Statement stmt) {
        stackFlujo.push(stmt);
        module.closeBlock(stmt.getFirstLine(), stmt.getLastLine(), numStmts);
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
	 * El PERFORM es inline si:
	 *    A: Es de la forma PERFORM 
	 *    B: No es de la forma  PERFORM ID .....
	 *    C: Es PERFORM ID TIMES
	 * @param stmt La sentencia PERFORM a analizar
	 * @return cierto si el perform es inline
	 */
	private boolean isInLine(Statement stmt) {
		ArrayList<Symbol> tokens = stmt.getSymbols();
		if (tokens.size() == 0) return true;
		
		// No es de la forma PERFORM ID [....]
		if (tokens.get(0).sym != OCSym.ID) return true;

	    // Es de la forma PERFORM ID
		if (tokens.size() == 1) return false;

		// Es de la forma PERFORM ID TIMES
		if (tokens.get(1).sym == OCSym.TIMES) return true;
		
		// No es de la forma PERFORM ID ...
		return false;
	}	

	private void calculatePerformComplexity(Statement stmt) {
		if (stmt.hasSymbol(OCSym.TIMES))    parrafo.incCiclomatic();
//		if (stmt.hasSymbol(OCSym.VARYING))  parrafo.incCiclomatic();
		if (stmt.hasSymbol(OCSym.UNTIL))    {
			parrafo.incCiclomatic();
/*			
			for (Symbol token : stmt.getSymbols()) {
				if (token.sym == OCSym.AND) parrafo.incCiclomatic();
				if (token.sym == OCSym.OR)  parrafo.incCiclomatic();
			}
*/			
		}
	}
	private void performOutline(Statement stmt) {
		int    thru    = stmt.getSymbolById(OCSym.THRU);
		Symbol s       = stmt.getSymbol(0);
		Symbol perform = stmt.getVerb();
		String nombre  = (String) s.value;
		
        injector.setPrevTrap(new Trap(TRAP.BEG_PERFORM, makeLiteral(nombre)));
		injector.loadTraps(perform.left, perform.right);
        injector.setNextTrap(new Trap(TRAP.END_PERFORM, makeLiteral(nombre)));
        
        module.addParagraphReference(nombre);
        //JGG Mirar esto. Caso procedure perform
        if (currBlock != null) currBlock.addPerform(nombre);
        
        if (thru != -1) {
        	Symbol t = stmt.getSymbol(thru + 1);
        	module.addParagraphReference((String) t.value);
        }
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
		if (s.sym != OCSym.PERFORM) return stmt;
		injector.setFirstTrap(new Trap(TRAP.BEG_PERFORM, s.left));
		injector.setNextTrap(new Trap(TRAP.END_PERFORM, s.left));
		return stmt;
	}
	
	public Statement fileAction(int action, Symbol base, Tokens files) {
        Statement stmt = new Statement(base, numStmts);
        stmt.addTokens(files);
        for (Symbol s : files.getTokens()) {
             Persistence f = module.getFile( (String) s.value);
             injector.setPrevTrap(new Trap(action, f.getPos() + 1));
        }
        return stmt;
	}
	
	public Statement fileAccess(int action, Symbol base, Symbol f) {
        Persistence p;
        
		Statement stmt = new Statement(base, numStmts);
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
        Statement stmt = new Statement(verbo);
        stmt.addSymbol(rutina);

	    String nombre = (String) rutina.value;
	    if (rutina.sym == OCSym.LITERAL) nombre = makeLiteral(nombre);

        injector.setPrevTrap(new Trap(TRAP.BEG_CALL, nombre));
        injector.setNextTrap(new Trap(TRAP.END_CALL, nombre));        

        // Insertar la dependencia
        
        int tipo    = CDG.DEP_MODULO;
        int subtipo = CDG.DEP_MOD_UNKNOW;
        
        if (rutina.sym == OCSym.ID) {
            Var v = module.getVariable(nombre);
            if (v == null) {
                subtipo = CDG.DEP_MOD_UNKNOW;
            }
            else {
                if (v.getValue() != null && v.isWritten() == false) {
                    subtipo = CDG.DEP_MOD_DYNAMIC;
                    nombre = (String) v.getValue();
                }
            }     
        }
        else {
            subtipo = CDG.DEP_MOD_STATIC;
            nombre = unmakeLiteral(nombre);
        }

        Dependencias deps = module.getDependencias();
        deps.addDependencia(nombre,  tipo, subtipo);        
        
        return stmt;
	}
	
	public void endProgram(Statement endProgram) {
        Trap trap = new Trap(TRAP.END_MODULE, makeLiteral(module.getName()));
        trap.setEndStatement(true);
        trap = injector.setPrevTrap(trap);
        injector.loadTraps(endProgram.getFirstLine(), 0);
//        hasEndProgram = true;
	}

	public void EndOfFile(Statement last, boolean hasEndCode) {
		trapEOF(last, hasEndCode);
        module.setStatements(numStmts);
        insertSDPFields();  // Debe ser lo ultimo
        setInjectorVars();
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
	    module.lastParagraph(last.getLastLine(), numStmts);
        module.lastBlock(last.getLastLine(), numStmts);
//JGG4	    if ( !parrafo.isExit() && !parrafo.isVirtual() && hasEndProgram == false) {
//JGG4	    	injector.setPrevTrap(new Trap(TRAP.END_PARAGRAPH, makeLiteral(parrafo.getName())));
//JGG4	    }

	    if (hasEndCode == false) {
            Trap trap = new Trap(TRAP.END_MODULE, makeLiteral(module.getName()));
            trap.setEndStatement(true);
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
	           case OCSym.IF: 
	               cause = TRAP.END_IF;
	               trap = new Trap(cause, bloques + 1);
	               injector.setPrevTrap(trap);
	               break;
               case OCSym.EVALUATE:
                   cause = TRAP.END_EVAL;
                   trap = new Trap(cause, bloques + 1);
                   injector.setPrevTrap(trap);
                   break;
	        }
	        module.endBlock(stmt.getFirstLine(), numStmts);
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
		return '\'' + txt + '\'';
	}
    private String unmakeLiteral(String txt) {
        return txt.substring(1, txt.length() - 1);
    }

	private void setInjectorVars() {
		String s_grp = "S" + (System.currentTimeMillis() / 100000);
        module
        injector.setVar("SDP-GROUP", s_grp);
        injector.setVar("MODULE"   , module.getName());
        injector.setVar("BLOCKS"   , module.getNumBloques());
        injector.setVar("PARRS"    , module.getNumParagraphs() + 1);
        injector.setVar("FILES"    , module.getNumFiles() + 1);
        injector.setVar("NPARRS"   , module.getNumParagraphs());
        injector.setVar("NFILES"   , module.getNumFiles());
        injector.setVar("ID1"      , module.getIdModule().substring( 0, 16)); 
    	injector.setVar("ID2"      , module.getIdModule().substring(16, 32));
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
		} else if (sections.hasScreen()) {
			line = sections.getScreen();
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
	                              stmt.getFirstLine(), 
	                              stmt.getLastLine(), 
	                              stmt.getFirstColumn());
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
}
