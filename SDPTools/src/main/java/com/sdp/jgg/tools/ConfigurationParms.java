package com.sdp.jgg.tools;

import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.ctes.CFG;

public class ConfigurationParms {

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
			    {" ", "l" , "load"    , ""  , CFG.TOOLS_CFG_LOAD    , Args.BOOLEAN,      "221"}    		
	           ,{"1", "u" , "unload"  , ""  , CFG.TOOLS_CFG_UNLOAD  , Args.BOOLEAN,  "204"}                    
	           ,{"1", "f" , "file"    , ""  , CFG.PROPS             , Args.FILE,  "224"}         
	           ,{"1", ""  , "full"    , ""  , CFG.PARSER_FORCE      , Args.BOOLEAN,  "224"}
	    };

}
