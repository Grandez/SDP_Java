/*
 * 
 */
package com.jgg.sdp.web.rest.services;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.web.json.*;

@Service
public class StatsService2 {

    @Autowired
    SDPAplicacionService applService;
    @Autowired
    SDPModuloService     moduloService;
    @Autowired
    MODVersionService    versionService;
    @Autowired
    SESSesionService     sesionService;
    
    private Stats stats;
    
    /**
     * Cargar estadisticas.
     *
     * @param appl
     *            the appl
     * @param grupo
     *            the grupo
     * @param from
     *            the from
     * @return the stats
     */
    public Stats cargarEstadisticas(Area appl, Set<Long> grupo, Timestamp from) {
        long beg = System.currentTimeMillis();
        stats = new Stats();
        stats.setAplicaciones(appl.hijos.size());
        SDPAplicacion app = applService.findById(appl.getId());
        Integer modulos = (app == null) ? 0 : app.getVolumen();
        
        for (int index = 0; index < appl.hijos.size(); index++) {
            modulos += appl.hijos.get(index).getModulos();
        }
        stats.setModulos(modulos);

        Set<Long> setMod = calcularModulosActivos(grupo);
        stats.setActivos(setMod.size());
        stats.setVariacion(calcularVariacion(grupo,from));
        stats.setSesiones(calcularSesiones(setMod, from));
        return stats;
    }
    
    private int calcularVariacion(Set<Long> grupo, Timestamp from) {
        int cuenta = 0;
        for (SDPModulo mod : moduloService.getModulosModificados(from)) {
            if (grupo.contains(mod.getIdAppl())) {
                cuenta += (mod.getEstado() == 1) ? 1 : -1;
            }
        }
        return cuenta;
    }

    private Set<Long> calcularModulosActivos(Set<Long> grupo) {
        Set<Long> setMods = new HashSet<Long>();
        
        for (SDPModulo mod : moduloService.getModulosActivos()) {
            if (grupo.contains(mod.getIdAppl())) setMods.add(mod.getIdModulo());
        }
        return setMods;
    }
    
    private long calcularSesiones(Set<Long> grupo, Timestamp from) {
        long cuenta = 0;
        for (Object[] tupla : sesionService.getCuentaSesiones(from)) {
            MODVersion version = versionService.getByVersion((Long) tupla[0]);
            if (grupo.contains(version.getIdModulo())) cuenta += (Long) tupla[1];
        }
        return cuenta;
    }
}
