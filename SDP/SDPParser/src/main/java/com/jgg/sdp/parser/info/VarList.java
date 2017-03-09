/**
 * Tabla de simbolos o variables<br> 
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.info;

import java.util.ArrayList;

public class VarList {

    private ArrayList<Variable> vars = new ArrayList<Variable>();
    
    public VarList (Variable v) {
        vars.add(v);
    }
    
    public VarList add(Variable v) {
        vars.add(v);
        return this;
    }
    
    public ArrayList<Variable> getVarList() {
        return vars;
    }
}
