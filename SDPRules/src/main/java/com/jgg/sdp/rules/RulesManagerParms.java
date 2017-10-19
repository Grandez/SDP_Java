package com.jgg.sdp.rules;

import com.jgg.sdp.common.config.Args;
import com.jgg.sdp.common.ctes.CFG;

public class RulesManagerParms {

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
            {"1", "u" , "unload"    , ""                 , CFG.RULES_UNLOAD  , Args.BOOLEAN,  "227"}
           ,{"1", "l" , "load"      , ""                 , CFG.RULES_LOAD    , Args.BOOLEAN,  "228"}
           ,{"1", "s" , "split"     , ""                 , CFG.RULES_SPLIT   , Args.STRING,   "270"}           
    };

}
