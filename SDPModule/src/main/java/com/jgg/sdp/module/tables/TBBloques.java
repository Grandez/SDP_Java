/**
 * Mantiene la tabla de la lista de bloques
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.ArrayList;

import com.jgg.sdp.module.items.Block;

public class TBBloques {

	private ArrayList<Block> bloques = new ArrayList<Block>();
	
	/**
     * Inserta un nuevo bloque en la tabla
     *
     * @param bloque  El bloque a insertar
     * @param endLine Linea de final del bloque anterior
     * @return Numero de bloques
     */
	public int addBloque(Block bloque, int endLine) {
		bloques.add(bloque);
		if (bloques.size() == 1) {
		    return 1;
		}

		setEndLine(2, bloque.getStatements(), endLine);
		return bloques.size();
	}

    /**
     * Establece la linea de inicio del bloque y la linea de fin del bloque
     * anterior si existe.
     *
     * @param begin
     *            Comienzo del bloque
     */
    public void beginBlock(int begin) {
        Block b = bloques.get(bloques.size() - 1);
        b.setBegin(begin);    
    }
	
    /**
     * Realiza el proceso de cierre de un bloque
     *
     * @param end
     *            Linea de finalizacion del bloque
     * @param stmts
     *            Numero de sentencias analizadas
     */
    public void endBlock(int end, int stmts) {
        Block b = bloques.get(bloques.size() - 1);
        if (b.getEnd() == -1) {
            b.setEnd(end);
            b.setStatements(stmts - b.getStatements());
        }
        if (b.getBegin() == -1) {
            b.setBegin(end);
        }
    }

    /**
     * Cierra un bloque
     *
     * @param beg
     *            Linea de inicio
     * @param end
     *            Linea de fin
     * @param stmts
     *            Numero de sentencias analizadas
     */
    public void closeBloque(int beg, int end, int stmts) {
    	if (bloques.size() == 0) return;
        Block b = bloques.get(bloques.size() - 1);
        if (b.getBegin() == -1) b.setBegin(beg);
        if (b.getEnd()   == -1) {
            b.setEnd  (end);
            b.setStatements(stmts - b.getStatements());
        }
    }

    /**
     * Actualiza el ultimo bloque de codigo pendiente.
     *
     * @param lastLine
     *            Ultima linea de codigo
     * @param stmts
     *            Numero de sentencias totales
     */
    public void lastBloque(int lastLine, int stmts) {
        if (bloques.size() == 0) return;
        setEndLine(1, stmts, lastLine);
    }

    private void setEndLine(int dep, int stmts, int endLine) {
        int nStmts = stmts;
        // Ajustar los bloques anteriores
        Block b = bloques.get(bloques.size() - dep);
        while (b.getEnd() == -1) {
            nStmts = b.getStatements();
            b.setStatements(stmts - b.getStatements());
            b.setEnd(endLine);
            // Caso especial de bloques vacios
            if (b.getBegin() == -1) b.setBegin(b.getEnd());
            stmts = nStmts;
            dep++;
            if (dep >= bloques.size()) return;
            b = bloques.get(bloques.size() - dep);
         }
    }

    public ArrayList<Block> getBloques() {
		return bloques;
	}
	
	public int getCount() {
		return bloques.size();
	}

	public Block getCurrentBlock() {
		if (bloques.size() == 0) return null;
		return bloques.get(bloques.size() - 1);
	}
	
}
