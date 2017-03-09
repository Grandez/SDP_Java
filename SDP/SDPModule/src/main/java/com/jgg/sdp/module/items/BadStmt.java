/**
 * Implementa una sentencia no permitida
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

public class BadStmt {

    private String stmt;
    private Integer begLine;
    private Integer endLine;
    private Integer columna;
    public String getStmt() {
        return stmt;
    }
    public void setStmt(String stmt) {
        this.stmt = stmt;
    }
    public Integer getBegLine() {
        return begLine;
    }
    public void setBegLine(Integer begLine) {
        this.begLine = begLine;
    }
    public Integer getEndLine() {
        return endLine;
    }
    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }
    public Integer getColumna() {
        return columna;
    }
    public void setColumna(Integer columna) {
        this.columna = columna;
    }
    
}
