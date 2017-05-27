/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.module.MODResumen;
import com.jgg.sdp.domain.services.module.MODResumenService;

@RestController
public class ModuloInfoController {

    @Autowired
    MODResumenService resumenService;
    
    @RequestMapping("/sentences/{idVersion}")
    public MODResumen getSentencesInfo(@PathVariable Long idVersion) {
        return resumenService.getResumen(idVersion);
    }

}
