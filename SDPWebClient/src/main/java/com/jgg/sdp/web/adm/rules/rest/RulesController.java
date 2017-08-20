package com.jgg.sdp.web.adm.rules.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.rules.RULGroupsService;
import com.jgg.sdp.web.core.*;
import com.jgg.sdp.web.json.*;

import com.jgg.sdp.web.WebCtes;

@RestController
public class RulesController {


    private Configuration cfg = DBConfiguration.getInstance();

    @Autowired
    private RULGroupsService grpService;    
    @Autowired
    private SDPModuloService modNamed;
    @Autowired
    private MODSummaryService sumNamed;

    @RequestMapping("admin/getRulesGroup")
    public List<ApplTree> getRulesGroup(@RequestHeader HttpHeaders headers) {
    	String lang = LANG.getLanguage(headers);
    	
        //return mountTree(0L);
        return null;
    }

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