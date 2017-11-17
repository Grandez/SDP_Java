package com.jgg.sdp;

import com.jgg.sdp.common.config.Args;
import com.jgg.sdp.common.ctes.CFG;

public class LoaderParms {

    public static final String parms[][] = {
     	    {""  , "rules"     , ""       , CFG.LOADER_TYPE     , "RULES"     ,  "280", "1", "0101" , ""}
     	   ,{""  , "config"    , ""       , CFG.LOADER_TYPE     , "CONFIG"    ,  "281", "0", "0102" , ""}	   
    	   ,{"l" , "load"      , ""       , CFG.LOADER_PROCESS  , "LOAD"      ,  "282", "1", "0201" , ""}
    	   ,{"u" , "unload"    , ""       , CFG.LOADER_PROCESS  , "UNLOAD"    ,  "283", "0", "0202" , ""}       
           ,{"c" , "clean"     , ""       , CFG.LOADER_CLEAN    , Args.BOOLEAN,  "284", "0", "02010", ""}
           ,{"s" , "split"     , ""       , CFG.LOADER_SPLIT    , Args.STRING ,  "285", "0", "01010", "group:item"}           
           ,{"o" , "out"       , ""       , CFG.LOADER_DIR      , Args.DIR    ,  "286", "0", "02010", ""}          
           ,{"t" , "type"      , ""       , CFG.LOADER_TYPE     , Args.STRING ,  "287", "0", "02010", "xml:props"}           
           ,{"f" , "file"      , ""       , CFG.LOADER_FILE     , Args.FILE   ,  "288", "0", "02010", ""}           
    };

}
