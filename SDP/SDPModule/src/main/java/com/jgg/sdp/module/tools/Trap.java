/**
 * Define un bjeto Trap
 *   
 * La mascara esta formada por un entero de dos bytes
 * byte 1: Indica el tipo de control realizado
 *         El bit 1 indica si es principio o fin (1)
 *         
 * Byte 2: Indica el tipo de proceso
 *   1 - Es una llamada a SDPTRAP
 *   2 - Es una operacion dentro del codigo: MOVE BLOCK
 *   4 - Es un acceso de lectura/escritura/etc
 *                   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tools;

import java.util.ArrayList;

import com.jgg.sdp.core.ctes.TRAP;

public class Trap {


	private final int UNDEF = 0;
	
	private ArrayList<String> lines = new ArrayList<String>();

	private int group = UNDEF;
	private int subType = UNDEF;
	private int type = UNDEF;
	private int line = 0;
	private int column = 0;
    private boolean endStatement = false;
    
	public Trap() {
		
	}
	
	/**
     * Crea un nuevo Trap del tipo indicado
     *
     * @param type Tipo de Trap
     */
	public Trap(int type) {
		setType(type);
		generateCode((Object[]) null);
	}
	
	/**
     * Crea un nuevo Trap del tipo indicado
     * Lo informa con las variables 
     *
     * @param type Tipo del Trap a crear
     * @param parms Lista de variables necesarias para construirlo
     */
	public Trap (int type, Object... parms) {
		setType(type);
		generateCode(parms);
	}

	/**
     * Inserta la linea de codigo en el Trap
     *
     * @param line Linea de codigo
     */
	public void addLine(String line) {
		lines.add(line);
	}
	
	/**
     * Inserta la linea de codigo en el Trap
     * en la posicion indicada
     *
     * @param pos  Posicion donde insertar la linea
     * @param line La linea de codigo a insertar
     */
	public void addLine(int pos, String line) {
		lines.add(pos, line);
	}
	
	/**
     * Incluye las lineas de codigo del trap al final de la lista .
     *
     * @param trap  Trap con el codigo a incluir
     */
	public void add(Trap trap) {
	   for (String line : trap.getTrapCode()) {
		   lines.add(line);
	   }
	}
	
	/**
     * Incluye las lineas de codigo del trap al inicio de la lista .
     *
     * @param trap
     *            Trap con el codigo a incluir
     */
	public void addBefore(Trap trap) {
		for (int index = trap.getLinesTrapCode() - 1; index > -1 ; index--) {
			lines.add(0, trap.getTrapCodeLine(index));
		}
	}
	
	public void setType(int type) {
		this.type = type;
		subType = type & 0xFF;
		group = type >> 8;
	}
	
	public int getType() {
		return type;
	}

	public int getGroup() {
		return group;
	}
	
	public int getSubType() {
		return subType;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setEndStatement(boolean endStatement) {
		this.endStatement = endStatement;
	}
	
	public ArrayList<String> getTrapCode() {
		if (endStatement) checkEndStatement();
		return lines;
	}
	
	public int getLinesTrapCode() {
		return lines.size();
	}
	
	/**
     * Obtiene una determina linea de codigo del Trap
     *
     * @param index La posicion de la linea en el Trap
     * @return la linea de codigo
     */
	public String getTrapCodeLine(int index) {
		if (index >= lines.size()) return null;
		return lines.get(index);
	}
	
	/**
     * Chequea si se debe establecer un fin de sentencia
     * Fin de sentencia => Acabar en punto
     *
     * @return true, Si se debe acabar con un punto
     */
	public boolean endStatement() {
		return endStatement;
	}
	

	private void generateCode(Object... parms) {
		String[] prms = makeStrings(parms);
		switch (group) {
		    case TRAP.CODE:   generateTrapCode(prms); break; 
		    case TRAP.TRAP:   generateTrapCall(prms); break;
		    case TRAP.ACCESS: generateTrapAccess(prms); break;
		}
		
	}
	
	private void generateTrapCode(String... parms) {
		switch (subType) {
		    case TRAP.PARR:  
		    	 lines.add("           ADD  1 TO PAR(" + parms[0] + ") ");
//JGG4		         lines.add("           MOVE 1 TO COB(" + parms[1] + ").");
		         break;
		    case TRAP.BLOQUE:   
		    	 lines.add("           MOVE 1 TO COB(" + parms[0] + ") ");
		    	 break;
		    case TRAP.PERFORM:
		         lines.add("           PERFORM");
		         break;
		    case TRAP.ENDPERFORM:
		   	     lines.add("           END-PERFORM");
		         break;
            case TRAP.IF:  
                 lines.add("           END-IF");
                 lines.add("           MOVE 1 TO COB(" + parms[0] + ")");
                 break;		    	  
            case TRAP.EVAL:  
                 lines.add("           END-EVALUATE");
                 lines.add("           MOVE 1 TO COB(" + parms[0] + ")");
                 break;                
            case TRAP.WORKINGS: 
		    	 mountWorking(); 
		    	 break;
		    case TRAP.FIRST:
		    	 mountFirstPerform(parms);
		    	 break;
		}
	}

	private void generateTrapCall(String... parms) {
		String rutina = parms[0];
		String largo;
		char   endp = ' ';

		if (type == TRAP.BEG_MODULE) {
		    endp = '.';
		}
		
		if (isLiteral(rutina)) {
			largo = Integer.toString(rutina.length() - 2);
		}
		else {
			largo = "LENGTH OF " + rutina;
		}	
			
	    lines.add(String.format("%sCALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                    "           ",  type, largo));				                 
        lines.add(String.format("                %s%c", rutina, endp));

//JGG4        if (type == TRAP.BEG_PARAGRAPH) {
//JGG4            lines.add("           ADD  1 TO PAR(" + parms[1] + ") ");
//JGG4        }


    }	
	
	private void generateTrapAccess(String... parms) {
	   int bit = 1;
       int count = 1;
	   while (bit != subType) {
		  bit <<= 1;
		  count++;
	   }
       lines.add(String.format("%sADD 1 TO ACC OF ${SDP-GROUP}(%s,%d)", 
    		                    "           ",  parms[0], count));
	}

	private void mountWorking() {
		setColumn(0);

		addLine("       01  ${SDP-GROUP}.");
		addLine("           03 TRAP    PIC X(08) VALUE 'SDPTRAPB'.");
		addLine("           03 NAME    PIC X(32) VALUE  '${MODULE}'.");
		addLine("           03 MD5ID.");
		addLine("              05 FILLER PIC X(16) VALUE '${ID1}'.");
		addLine("              05 FILLER PIC X(16) VALUE '${ID2}'.");
		addLine("           03 SDPCNT.");		
		addLine("              05 NBLK BINARY-LONG VALUE ${BLOCKS}.");
		addLine("              05 NPAR BINARY-LONG VALUE ${NPARRS}.");
		addLine("              05 NPRS BINARY-LONG VALUE ${NFILES}.");		
		addLine("           03 COB OCCURS ${BLOCKS} TIMES PIC 9(01) VALUE ZEROS.");
        addLine("           03 PAR OCCURS ${PARRS}  TIMES BINARY-LONG VALUE ZEROS.");		
		addLine("           03 PRS OCCURS ${FILES} TIMES.");
		addLine("              05 ACC OCCURS 8 TIMES BINARY-LONG VALUE ZEROS.");

		addLine("");        
	}
	
	private void mountFirstPerform(String... parms) {
		String parrafo = parms[0];
		String modulo = parms[1];
		int largo = modulo.length();
		
		setColumn(0);
		
	    lines.add(String.format("%sCALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                "           ",  TRAP.BEG_PERFORM, largo));				                 
        lines.add(String.format("                \'%s\'", parrafo));
        
        lines.add(String.format("           PERFORM %s", parrafo));
	    lines.add(String.format("%sCALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                "           ",  TRAP.END_MODULE, largo));				                 
        lines.add(String.format("                \'%s\'", modulo));
        lines.add(String.format("           GOBACK."));
	}
	
	private boolean isLiteral (String txt) {
		if (txt.charAt(0) == '\'') return true;
		if (txt.charAt(0) == '\"') return true;
		return false;
	}
	private void checkEndStatement() {
		if (lines.size() == 0) return;
		String l = lines.get(lines.size() - 1);
		if (!l.endsWith(".")) lines.add("            .");
	}
	
	private String[] makeStrings(Object ...vars) {
		if (vars == null) return null;
		String[] parms = new String[vars.length];
		for (int idx = 0; idx < vars.length; idx++) {
			if (vars[idx] instanceof String)  parms[idx] = (String) vars[idx];
			if (vars[idx] instanceof Integer) parms[idx] = Integer.toString((Integer) vars[idx]);
			if (vars[idx] instanceof Long)    parms[idx] = Long.toString((Long) vars[idx]);
		}
		return parms;
	}
}
