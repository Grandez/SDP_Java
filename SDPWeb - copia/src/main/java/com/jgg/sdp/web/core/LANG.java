/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.jgg.sdp.core.ctes.SYS;

public class LANG {

    public static String getLanguage(HttpHeaders headers) {
        List<String> lang = headers.get("accept-language");
        if (lang == null) return SYS.DEF_LANG;
        if (lang.size() == 0) return SYS.DEF_LANG;
        int pos = lang.get(0).indexOf('-');
        if (pos == -1) return lang.get(0).substring(0, 2).toUpperCase();
        return lang.get(0).substring(pos + 1, pos + 3);
    }

}
