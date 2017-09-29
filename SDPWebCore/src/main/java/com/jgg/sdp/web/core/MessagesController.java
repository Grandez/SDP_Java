/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.services.web.*;
import com.jgg.sdp.domain.web.*;

@RestController
public class MessagesController {

	@Autowired
	WEBLabelsService lblService;
	
    @RequestMapping("/labels/{block}")
    public List<String> getLabels(@PathVariable  Integer block,
                                  @RequestHeader HttpHeaders headers) {
        List<String> msgs = new ArrayList<String>();
    	String lang[] = LANG.getLanguage(headers);
        for (WEBLabel lbl : lblService.listByGroup(lang, block)) {
        	msgs.add(lbl.getTxtKey());
        	msgs.add(lbl.getTxtValue());
        }
        return msgs;
    }
	
    @RequestMapping("/messages")
    public List<String> getMessages(@RequestHeader HttpHeaders headers) {
        ResourceBundle msg = null;
        List<String> msgs = new ArrayList<String>();
/*        
        String[] lang = LANG.getLanguage(headers);
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
*/        
        return msgs;
    }
}


