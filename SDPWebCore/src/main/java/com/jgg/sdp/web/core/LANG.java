/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.jgg.sdp.core.ctes.SYS;

public class LANG {

    public static String[] getLanguage(HttpHeaders headers) {
    	String[] l = new String[2];
        List<String> lang = headers.get("accept-language"); 
        if (lang == null) {
        	l[0] = SYS.DEF_LANG;
        	l[1] = SYS.DEF_LANG;
        	return l;
        }
        
        if (lang.size() == 0) {
        	l[0] = SYS.DEF_LANG;
        	l[1] = SYS.DEF_LANG;
        	return l;
        }
        
        String def = lang.get(0);
        int pos = def.indexOf('-');
        
        if (pos == -1) {
        	l[0] = def;
        	l[1] = def;
        	return l;
        }
        
    	l[0] = def.substring(0, pos);
    	l[1] = def.substring(pos + 1, pos + 3);
    	return l;
    }

}
