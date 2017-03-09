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
package com.jgg.sdp.parser.tools;

import java.util.ArrayList;

import com.jgg.sdp.core.ctes.TRAP;

public class Trap {


	private final int UNDEF = 0;
	
	private ArrayList<String> lines = new ArrayList<String>();

	private int group = UNDEF;
	private int subType = UNDEF;
	private int type = UNDEF;
	private int flag = 15;
	private int line = 0;
	private int column = 0;
    private boolean endStatement = false;
    
	public Trap() {
		
	}
	
	public Trap(int type) {
		setType(type);
		setFlag();
		generateCode((Object[]) null);
	}
	
	public Trap (int type, Object... parms) {
		setType(type);
		setFlag();
		generateCode(parms);
	}

	public Trap load(int type, Object... parms) {
        setType(type);
        setFlag();
		generateCode(parms);
		return this;
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public void addLine(int pos, String line) {
		lines.add(pos, line);
	}
	
	public void add(Trap trap) {
	   for (String line : trap.getTrapCode()) {
		   lines.add(line);
	   }
	}
	
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
	
	public String getTrapCodeLine(int index) {
		if (index >= lines.size()) return null;
		return lines.get(index);
	}
	
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
		String margin = "           ";
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

		lines.add(String.format(""));		
		lines.add(String.format("%sIF FLG(%d) = 1 THEN ", margin, flag));
	    lines.add(String.format("%s   CALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                 margin,  type, largo));				                 
        lines.add(String.format("%s        %s", margin, rutina));
		lines.add(String.format("%sEND-IF%c", margin, endp));
	    lines.add(String.format(""));
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
		addLine("           03 MODN    PIC X(32) VALUE '${MODULE}'.");
		addLine("           03 MD5ID.");
		addLine("              05 FILLER PIC X(16) VALUE '${ID1}'.");
		addLine("              05 FILLER PIC X(16) VALUE '${ID2}'.");
		addLine("           03 FLG-DATA  PIC 9(16) VALUE '${FLAGS}'.");
		addLine("           03 FLG REDEFINES FLG-DATA PIC 9(01) OCCURS 16.");
		addLine("           03 DEPTH BINARY-LONG VALUE ${DEPTH}.");		
		addLine("           03 SDPCNT.");		
		addLine("              05 NBLK BINARY-LONG VALUE ${BLOCKS}.");
		addLine("              05 NPAR BINARY-LONG VALUE ${NPARRS}.");
		addLine("              05 NPRS BINARY-LONG VALUE ${NFILES}.");		
		addLine("           03 COB OCCURS ${BLOCKS} TIMES PIC 9(01) VALUE ZEROS.");
        addLine("           03 PAR OCCURS ${PARRS} TIMES BINARY-LONG VALUE ZEROS.");		
		addLine("           03 PRS OCCURS ${FILES} TIMES.");
		addLine("              05 ACC OCCURS 8 TIMES BINARY-LONG VALUE ZEROS.");

		addLine("");        
	}
	
	private void mountFirstPerform(String... parms) {
		String margin = "           ";		
		String parrafo = parms[0];
		String modulo = parms[1];
		int largo = modulo.length();
		
		setColumn(0);

		lines.add(String.format("%sIF FLG(%d) = 1 THEN ", margin, TRAP.FLG_PERFORM));
		lines.add(String.format("%s   CALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                margin,  TRAP.BEG_PERFORM, largo));				                 
        lines.add(String.format("%s        \'%s\'", margin, parrafo));
        lines.add(String.format("%sELSE ", margin));
		lines.add(String.format("%s   IF FLG(%d) = 1 THEN ", margin, TRAP.FLG_MODULE));		
	    lines.add(String.format("%s      CALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                margin,  TRAP.BEG_MODULE, largo));				                 
        lines.add(String.format("%s           \'%s\'", margin, modulo));
		lines.add(String.format("%s   END-IF", margin));
		lines.add(String.format("%sEND-IF", margin));
        
		lines.add(String.format(" "));
        lines.add(String.format("%sPERFORM %s", margin, parrafo));
        lines.add(String.format(" "));
        
        lines.add(String.format("%sIF FLG(%d) = 1 OR FLG(%d) = 1 THEN ", margin, TRAP.FLG_PERFORM, TRAP.FLG_MODULE));
	    lines.add(String.format("%s   CALL TRAP OF ${SDP-GROUP} USING %d %s ${SDP-GROUP}",
                                margin,  TRAP.END_MODULE, largo));				                 
        lines.add(String.format("%s  \'%s\'", margin, modulo));
        lines.add(String.format("%sEND-IF", margin));
        lines.add(String.format("           GOBACK."));
        lines.add(String.format(" "));
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
	
	private void setFlag() {
		switch (type) {
		    case TRAP.BEG_MODULE:
		    case TRAP.END_MODULE:  flag = TRAP.FLG_MODULE; break;
		    case TRAP.BEG_PERFORM:
		    case TRAP.END_PERFORM: flag = TRAP.FLG_PERFORM; break;
		    case TRAP.BEG_CALL:
		    case TRAP.END_CALL:    flag = TRAP.FLG_CALL;    break;
		    case TRAP.BEG_SQL:
		    case TRAP.END_SQL:     flag = TRAP.FLG_SQL;     break;
		    case TRAP.BEG_CICS:
		    case TRAP.END_CICS:    flag = TRAP.FLG_CICS;    break;
		    default:               flag = TRAP.FLG_UNDEF;
		}
	}
}
