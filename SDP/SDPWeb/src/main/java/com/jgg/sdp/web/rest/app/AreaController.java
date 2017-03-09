package com.jgg.sdp.web.rest.app;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.web.rest.services.*;

@RestController
public class AreaController {

    @Autowired
    AplicacionService aplicacionService;
    
    private Configuration cfg = DBConfiguration.getInstance();

    @RequestMapping("/area/{id}")
    public Area getArea(@PathVariable Long id) {
        return getArea(id, cfg.getInteger(CFG.DATE_INTERVAL));
    }
    
    @RequestMapping("/area/{id}/{rango}")
    public Area getArea(@PathVariable Long id, @PathVariable Integer rango) {
        return aplicacionService.getArea(id, rango);
    }
}