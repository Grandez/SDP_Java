package com.jgg.sdp.ivp;

import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.ctes.CFG;

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
            {" ", "i" , "input"  , "IVP_INPUT"        , CFG.IVP_INPUT    , "1" , "250" , Args.FILE}                    
    };

}
