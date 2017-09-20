package com.jgg.sdp.analyzer;

import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.ctes.CFG;

public class AnalyzerParms {

    /* Tabla de parametros aceptados
     * 0 - valor simbolico
     * 1 - parametro corto
     * 2 - parametro largo
     * 3 - variable de entorno
     * 4 - clave de configuracion
     * 5 - tipo de dato
     * 6 - Claves de mensajes
     */

    public static final String parms[][] = {
            {"1", "l" , "local"     , ""                 , CFG.PARSER_LOCAL  , Args.BOOLEAN,  "225"}
     	   ,{" ", "p" , "pending"   , ""                 , CFG.PARSER_PENDING, Args.BOOLEAN,  "226"}   
           ,{"1", ""  , "force"     , ""                 , CFG.PARSER_FORCE  , Args.BOOLEAN,  "214"}     	   
    	   ,{" ", "i" , "input"     , "SDP_INPUT"        , CFG.DIR_INPUT     , Args.DIR,      "204"}    				    
		   ,{" ", "l" , "left"      , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT   , Args.NUMBER,   "206"}	   
		   ,{" ", "r" , "right"     , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT  , Args.NUMBER,   "207"}		   		   
		   ,{" ", "n" , "no"        , "SDP_NO_SEND"      , CFG.SEND_NONE     , Args.BOOLEAN,  "261"}		   
		   ,{" ", "c" , "nocopy"    , "SDP_NO_COPY"      , CFG.SEND_COPY     , Args.BOOLEAN,  "262"}		   
		   ,{" ", "d" , "noinclude" , "SDP_NO_INCLUDE"   , CFG.SEND_INCLUDE  , Args.BOOLEAN,  "263"}		   
		   ,{" ", "x" , "exclude"   , "SDP_EXCLUDE"      , CFG.FILE_IGNORE   , Args.FILE,     "265"}		       		    		
                             
    };

}
