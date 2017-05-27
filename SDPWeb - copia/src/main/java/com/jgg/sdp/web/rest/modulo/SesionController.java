/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class SesionController {
    
    @Autowired
    SDPModuloService moduloService;

    @Autowired
    MODVersionService versionService;

    @Autowired
    SESSesionService sesionService;

    private Configuration cfg = DBConfiguration.getInstance();    
    
    /**
     * Gets the sesion info.
     *
     * @param idModulo
     *            the id modulo
     * @return the sesion info
     */
    @RequestMapping("/sesion/{idModulo}")
    public List<SESSesion> getSesionInfo(@PathVariable Long idModulo) {
        return getSesionInfo(idModulo, cfg.getInteger(CFG.DATE_INTERVAL));
    }

    /**
     * Gets the sesion info.
     *
     * @param idModulo
     *            the id modulo
     * @param rango
     *            the rango
     * @return the sesion info
     */
    @RequestMapping("/sesion/{idModulo}/{rango}")
    public List<SESSesion> getSesionInfo(@PathVariable Long idModulo, @PathVariable Integer rango) {
        Long version = moduloService.getCurrentVersion(idModulo);
        
        if (version == 0L) return new ArrayList<SESSesion>();
        
        Timestamp inicio = Fechas.calculaInicio(rango);

        return sesionService.getSesionesByVersion(version, inicio);
    }

}
