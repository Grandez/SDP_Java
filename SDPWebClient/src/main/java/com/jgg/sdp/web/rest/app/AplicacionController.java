/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.tools.Fechas;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.web.rest.services.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;

@RestController
public class AplicacionController {

    @Autowired
    private AplicacionService aplicacionService;

    @Autowired
    private SDPModuloService modNamed;
    @Autowired
    private MODVersionService versionNamed;
//    @Autowired
//    private SESSesionNamed sesNamed;
    
    private Configuration cfg = DBConfiguration.getInstance();

    /**
     * Gets the aplicacion.
     *
     * @param id
     *            the id
     * @return the aplicacion
     */
    @RequestMapping("/appl/{id}")
    public Area getAplicacion(@PathVariable Long id) {
        return getAplicacion(id, cfg.getInteger(CFG.DATE_INTERVAL));
    }
    
    /**
     * Gets the aplicacion.
     *
     * @param id
     *            the id
     * @param rango
     *            the rango
     * @return the aplicacion
     */
    @RequestMapping("/appl/{id}/{rango}")
    public Area getAplicacion(@PathVariable Long id, @PathVariable Integer rango) {
        Area area = aplicacionService.getArea(id, rango);
        
/*        
        Set<Long> grupo = modNamed.getConjuntoActivo(id);
        Timestamp inicio = Fechas.calculaInicio(rango);
        HashMap<Long, Appl> hash = loadModulos(area, id);
        loadEjecuciones(area, hash, grupo, inicio);
*/        
        return area;
    }

    private HashMap<Long, Appl> loadModulos(Area area, Long id) {
        HashMap<Long, Appl> h = new HashMap<Long,Appl>();
/*        
        area.hijos = new ArrayList<Appl>();
        for (SDPModulo mod : modNamed.getModulesByAppl(id)) {
            Appl app = new Appl();
            app.setId(mod.getIdModulo());
            app.setNombre(mod.getNombre());
            app.setSesiones(0L);
            area.hijos.add(app);
            h.put(mod.getIdModulo(), app);
        }
*/        
        return h;
    }
    
    private void loadEjecuciones( Area area 
                                 ,HashMap<Long, Appl> hash
                                 ,Set<Long> grupo
                                 ,Timestamp from) {
//        for (Object[] data : sesService.getTotalSesionesByVersion(from)) {
//            MODVersion ver = versionService.getByVersion((Long) data[0]);
//            if (grupo.contains(ver.getIdModulo())) {
//                hash.get(ver.getIdModulo()).incSesiones((Long)data[1]);
//            }
//        }
    }
    
}
