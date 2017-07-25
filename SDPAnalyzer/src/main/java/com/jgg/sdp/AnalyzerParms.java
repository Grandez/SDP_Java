package com.jgg.sdp;

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
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT     , Args.DIR,      "221"}    		
           ,{"1", "l" , "local"  , ""                 , CFG.PARSER_LOCAL  , Args.BOOLEAN,  "204"}                    
           ,{"1", ""  , "force"  , ""                 , CFG.PARSER_FORCE  , Args.BOOLEAN,  "224"}         
    };

}
