package com.jgg.sdp.analyzer;

import com.jgg.sdp.common.config.Args;
import com.jgg.sdp.common.ctes.CFG;

public class AnalyzerParms {

    /* Tabla de parametros aceptados
     * 0 - parametro corto
     * 1 - parametro largo
     * 2 - variable de entorno
     * 3 - clave de configuracion
     * 4 - tipo de dato /Xn informa con n
     * 6 - Claves de mensajes
     */

    public static final String parms[][] = {
            {"l" , "local"     , ""                 , CFG.PARSER_MODE   , "X1",          "225", "0", "", ""}
     	   ,{"p" , "pending"   , ""                 , CFG.PARSER_PENDING, Args.BOOLEAN,  "226", "0", "", ""}
           ,{""  , "force"     , ""                 , CFG.PARSER_FORCE  , Args.BOOLEAN,  "214", "0", "", ""}
    	   ,{"i" , "input"     , "SDP_INPUT"        , CFG.DIR_INPUT     , Args.DIR,      "204", "0", "", ""}	    
		   ,{"l" , "left"      , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT   , Args.NUMBER,   "206", "0", "", ""}
		   ,{"r" , "right"     , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT  , Args.NUMBER,   "207", "0", "", ""}		   		   
		   ,{"n" , "no"        , "SDP_NO_SEND"      , CFG.SEND_NONE     , Args.BOOLEAN,  "261", "0", "", ""}
		   ,{"c" , "nocopy"    , "SDP_NO_COPY"      , CFG.SEND_COPY     , Args.BOOLEAN,  "262", "0", "", ""}
		   ,{"d" , "noinclude" , "SDP_NO_INCLUDE"   , CFG.SEND_INCLUDE  , Args.BOOLEAN,  "263", "0", "", ""}
		   ,{"x" , "exclude"   , "SDP_EXCLUDE"      , CFG.FILE_IGNORE   , Args.FILE,     "265", "0", "", ""}	    				   
    };

}
