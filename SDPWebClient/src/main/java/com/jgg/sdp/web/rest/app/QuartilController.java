/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.session.SESSesion;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class QuartilController {

    @Autowired
    SDPModuloService  moduloService;
    @Autowired
    MODVersionService  versionService;
    @Autowired
    SESSesionService   sesionService;
    
    private Configuration cfg = DBConfiguration.getInstance();
    
    /**
     * Gets the box graph.
     *
     * @param id
     *            the id
     * @return the box graph
     */
    @RequestMapping("/box/{id}")
    public Quartiles getBoxGraph(@PathVariable Long id) {
        return getBoxGraph(id, cfg.getInteger(CFG.DATE_INTERVAL));
    }
    
    /**
     * Gets the box graph.
     *
     * @param id
     *            the id
     * @param rango
     *            the rango
     * @return the box graph
     */
    @RequestMapping("/box/{id}/{rango}")
    public Quartiles getBoxGraph(@PathVariable Long id, @PathVariable Integer rango) {
        Quartiles datos = new Quartiles();
        Timestamp from = Fechas.calculaInicio(rango);
        for (SDPModulo mod : moduloService.getModulesByAppl(id)) {
            Quartil q = new Quartil();
            q.setIdModulo(mod.getIdModulo());
            q.setNombre(mod.getNombre());
            MODVersion ver = versionService.getCurrentVersion(mod.getIdModulo());
            if (ver != null) {
                q.setIdVersion(ver.getIdVersion());
                calculaQuartiles(q, ver.getIdVersion(), from);
                if (q.getTotal() > 0) datos.addQuartil(q);
            }    
        }

        return datos;
    }
    
    private void calculaQuartiles(Quartil q, Long idVersion, Timestamp from) {
        List<SESSesion> sesiones = sesionService.getSesionesByElapsed(idVersion, from);
        if (sesiones == null || sesiones.size() == 0) return;
        int size = sesiones.size();
        q.setTotal(size);

        q.setQuartil(0, sesiones.get(0).getElapsed() / 1000);
        q.setQuartil(1, sesiones.get(size / 4).getElapsed() / 1000);
        q.setQuartil(2, sesiones.get(size / 2).getElapsed() / 1000);
        q.setQuartil(3, sesiones.get(size * 3/ 4).getElapsed() / 1000);
        q.setQuartil(4, sesiones.get(size - 1).getElapsed() / 1000);
    }

}
