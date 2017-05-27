/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.web.rest.services.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;

@RestController
public class AreaController {

    @Autowired
    AplicacionService aplicacionService;
    
    private Configuration cfg = DBConfiguration.getInstance();

    /**
     * Gets the area.
     *
     * @param id
     *            the id
     * @return the area
     */
    @RequestMapping("/area/{id}")
    public Area getArea(@PathVariable Long id) {
        return getArea(id, cfg.getInteger(CFG.DATE_INTERVAL));
    }
    
    /**
     * Gets the area.
     *
     * @param id
     *            the id
     * @param rango
     *            the rango
     * @return the area
     */
    @RequestMapping("/area/{id}/{rango}")
    public Area getArea(@PathVariable Long id, @PathVariable Integer rango) {
        return aplicacionService.getArea(id, rango);
    }
}