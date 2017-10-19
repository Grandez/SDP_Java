package com.jgg.sdp.trapper;

import com.jgg.sdp.common.config.Args;
import com.jgg.sdp.common.ctes.CFG;

public class TrapperParms {

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
		    {" ", "i" , "input"     , "SDP_INPUT"        , CFG.DIR_INPUT     , Args.DIR,      "204"}    		
		   ,{" ", "o" , "output"    , "SDP_OUTPUT"       , CFG.DIR_OUTPUT    , Args.DIR,      "205"}		    
		   ,{" ", "l" , "left"      , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT   , Args.NUMBER,   "206"}	   
		   ,{" ", "r" , "right"     , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT  , Args.NUMBER,   "207"}		   		   
		   ,{" ", "t" , "type"      , "SDP_DATA_TYPE"    , CFG.FILE_TYPE     , Args.NUMBER,   "230"}
		   ,{" ", "j" , "jms"       , "SDP_JMS_TYPE"     , CFG.JMS_TYPE      , Args.STRING,   "240"}		   
		   ,{" ", "u" , "url"       , "SDP_JMS_HOST"     , CFG.JMS_HOST      , Args.STRING,   "241"}
		   ,{" ", "p" , "port"      , "SDP_JMS_PORT"     , CFG.JMS_PORT      , Args.NUMBER,   "242"}
		   ,{" ", "s" , "service"   , "SDP_JMS_SERVICE"  , CFG.JMS_PERSISTER , Args.NUMBER,   "242"}		   
		   ,{" ", "n" , "no"        , "SDP_NO_SEND"      , CFG.SEND_NONE     , Args.BOOLEAN,  "261"}		   
		   ,{" ", "c" , "nocopy"    , "SDP_NO_COPY"      , CFG.SEND_COPY     , Args.BOOLEAN,  "262"}		   
		   ,{" ", "d" , "noinclude" , "SDP_NO_INCLUDE"   , CFG.SEND_INCLUDE  , Args.BOOLEAN,  "263"}		   
		   ,{" ", "x" , "exclude"   , "SDP_EXCLUDE"      , CFG.FILE_IGNORE   , Args.FILE,     "265"}		   
    };

}
