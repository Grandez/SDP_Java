/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.log.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.session.*;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class AlertasController {

    @Autowired
    SESSesionService sesionService;
    @Autowired
    MODVersionService versionService;
    @Autowired
    SDPAplicacionService appService;
    @Autowired
    SDPModuloService moduloService;
    @Autowired
    LOGOutputService logService;
    
    /**
     * Cargar alertas.
     *
     * @param id
     *            the id
     * @param rango
     *            the rango
     * @return the alertas
     */
    @RequestMapping("/alertas/{id}/{rango}")
    public Alertas cargarAlertas(@PathVariable Long id, @PathVariable Integer rango) {    
        Set<Long>     grupo  = appService.getConjuntoActivo(id);
        Timestamp     inicio = Fechas.calculaInicio(rango);
        
        Alertas alertas = new Alertas();
        alertas.setFallidas(obtenerFallidas(grupo, id, inicio));
        cargaAlertas(alertas, grupo, inicio);
//        alertas.setElapsedTime(obtenerExcesoElapsed(grupo, inicio));
//        alertas.setCpuTime(obtenerExcesoCpu(grupo, inicio));
//        alertas.setSuspendTime(obtenerExcesoSuspend(grupo, inicio));
//        alertas.setMedias(obtenerExcesoMedias(grupo, inicio));
        return alertas;
    }
    
    /**
     * Obtener fallidas.
     *
     * @param grupo
     *            the grupo
     * @param idAppl
     *            the id appl
     * @param from
     *            the from
     * @return the list
     */
    public List<SESSesion> obtenerFallidas(Set<Long> grupo, Long idAppl, Timestamp from) {
        List<SESSesion> lista = sesionService.getFallidos(from);
        for (int index = 0; index < lista.size(); index++) {
            if (inArea(lista.get(index), grupo)) {
                lista.remove(index--);
            }
        }
        return lista;
    }
    
    private void cargaAlertas(Alertas alertas, Set<Long> grupo, Timestamp inicio) {
        for (LOGLogging l : logService.getAlertas(inicio)) {
            if (grupo.contains(l.getIdAppl())) {
                Tiempos t = new Tiempos();
                t.setIdAppl(l.getIdAppl());
                t.setIdModulo(l.getIdModulo());
                t.setIdVersion(l.getIdVersion());
                t.setSesion(l.getDato(0));
                alertas.addAlerta(l.getIdMsg(), t);
            }
        }
        
    }
    private boolean inArea(SESSesion ses, Set<Long> grupo) {
        SDPModulo mod = getModulo(ses);
        if (mod == null) return false;
        
        return grupo.contains(mod.getIdAppl());
    }
    
    private SDPModulo getModulo(SESSesion ses) {
        MODVersion ver = versionService.getByVersion(ses.getIdVersion());
        if (ver == null) return null;
        return moduloService.findById(ver.getIdModulo());
    }
    
    private List<Tiempos> loadTiempos(Set<Long> grupo, int code, Timestamp from) {
        List<Tiempos> lista = new ArrayList<Tiempos>();
//JGG        

        /*
        for (LOGLogging l : logService.listByCode(code, from)) {
            if (grupo.contains(l.getIdAppl())) {
                Tiempos t = new Tiempos();
                t.setIdAppl(l.getIdAppl());
                t.setIdModulo(l.getIdModulo());
                t.setIdVersion(l.getIdVersion());
                t.setSesion(l.getDato(0));
                lista.add(t);
            }
        }
        for (Tiempos t : lista) {
           SESSesion s = sesionService.findBySession(t.getSesion());
           if (s != null) {
               t.setCpu(s.getCpu());
               t.setElapsed(s.getElapsed());
               t.setEscrito(s.getEscrito());
               t.setFecha(s.getTms());
               t.setSuspend(s.getSuspend());
           }
        }
        */
        return lista;
    }
}
