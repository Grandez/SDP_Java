package com.jgg.sdp.rules;

import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.ctes.CFG;

public class LoadRulesParms {

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
            {"1", "u" , "unload"    , ""                 , CFG.PARSER_LOCAL  , Args.BOOLEAN,  "225"}
    };

}
