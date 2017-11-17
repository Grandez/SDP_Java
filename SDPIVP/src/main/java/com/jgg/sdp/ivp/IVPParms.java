package com.jgg.sdp.ivp;

import com.jgg.sdp.common.config.Args;
import com.jgg.sdp.common.ctes.CFG;

public class IVPParms {

    /* Tabla de parametros aceptados
     * 0 - valor simbolico
     * 1 - parametro corto
     * 2 - parametro largo
     * 3 - variable de entorno
     * 4 - clave de configuracion
     * 5 - Requiere valor (0 - no)
     * 6 - tipo de dato
     */

    public static final String parms[][] = { 
            {""  , "log"       , ""       , CFG.IVP_LOG         , Args.NUMBER  ,  "229", "0", ""     , "0-5"}                    
           ,{""  , "full"      , ""       , CFG.IVP_STOP_ON_ERR , "X0"         ,  "227", "1", "0101" , ""}            
           ,{""  , "simple"    , ""       , CFG.IVP_STOP_ON_ERR , "X1"         ,  "228", "1", "0102" , ""}
    };

}
