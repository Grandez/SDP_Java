/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessagesController {

    @RequestMapping("/messages")
    public List<String> getMessages(@RequestHeader HttpHeaders headers) {
        ResourceBundle msg = null;
        List<String> msgs = new ArrayList<String>();
        
        String lang = LANG.getLanguage(headers);
        try {
            msg = ResourceBundle.getBundle("webMessages", new Locale(lang.toLowerCase()));
        }
        catch (Exception e) {
            msg = ResourceBundle.getBundle("webMessages");
        }
        
        @SuppressWarnings("rawtypes")
        Enumeration bundleKeys = msg.getKeys();

        while (bundleKeys.hasMoreElements()) {
            String key = (String)bundleKeys.nextElement();
            String value = msg.getString(key);
            msgs.add(key);
            msgs.add(value);
//            msgs.add(new WebMsg(key, value));
        }
        return msgs;
    }
}


