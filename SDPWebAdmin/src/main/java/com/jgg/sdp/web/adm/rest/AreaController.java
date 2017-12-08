package com.jgg.sdp.web.adm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class AreaController {

//    @Autowired
//    private CFGCodeService codeService;    
	
    @RequestMapping("/areas")
    public void getConfiguration(@RequestHeader HttpHeaders headers) {

    }

}
