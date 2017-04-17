/**
 * Mantiene la tabla de la lista de sentencias no permitidas
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.ArrayList;

import com.jgg.sdp.module.items.BadStmt;

public class TBBadStmts {

    private ArrayList<BadStmt> badStmts = new ArrayList<BadStmt>();
    
    /**
     * Añade una sentencia no permitida a la lista
     *
     * @param stmt El objeto BadStmt con la sentencia no permitida
     */
    public void addStmt(BadStmt stmt) {
        badStmts.add(stmt);
    }
    public ArrayList<BadStmt> getBadStmts() {
        return badStmts;
    }
}
