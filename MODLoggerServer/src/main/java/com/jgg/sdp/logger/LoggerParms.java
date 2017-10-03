package com.jgg.sdp.logger;

import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.ctes.CFG;

public class LoggerParms {

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
     	    {" ", "p" , "port"      , ""                 , CFG.LOGGER_PORT   , Args.NUMBER,   "226"}   
           ,{"1", ""  , "force"     , ""                 , CFG.PARSER_FORCE  , Args.BOOLEAN,  "214"}     	   
    };

}
