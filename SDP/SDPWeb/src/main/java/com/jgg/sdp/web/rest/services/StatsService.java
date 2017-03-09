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
import com.jgg.sdp.domain.session.SESSesion;
import com.jgg.sdp.web.json.*;

@Service
public class StatsService {

    @Autowired
    SDPModuloService moduloService;
    @Autowired
    MODVersionService versionService;
    @Autowired
    SESSesionService sesionService;
    
    private Stats stats;
    
    public Stats cargarEstadisticas(Area appl, Set<Long> grupo, Timestamp from) {
        stats = new Stats();
        stats.setAplicaciones(appl.hijos.size());
        
        Integer modulos = 0;
        for (int index = 0; index < appl.hijos.size(); index++) {
            modulos += appl.hijos.get(index).getModulos();
        }
        stats.setModulos(modulos);
        stats.setActivos(calcularModulosActivos(grupo));
        stats.setVariacion(calcularVariacion(grupo,from));
        stats.setSesiones(calcularSesiones(grupo, from));
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
    
    private int calcularModulosActivos(Set<Long> grupo) {
        int cuenta = 0;
        for (SDPModulo mod : moduloService.getModulosActivos()) {
            if (grupo.contains(mod.getIdAppl())) cuenta++;
        }
        return cuenta;
    }
    private long calcularSesiones(Set<Long> grupo, Timestamp from) {
        long cuenta = 0;
        HashMap<Long, Long> modulos = new HashMap<Long, Long>();
        
        for (SESSesion ses : sesionService.getSesiones(from)) {
            MODVersion version = versionService.getByVersion(ses.getIdVersion());
            Long appl = modulos.get(version.getIdModulo());
            if (appl == null) {
               SDPModulo mod = moduloService.findById(version.getIdModulo());
               modulos.put(mod.getIdModulo(), mod.getIdAppl());
               appl = mod.getIdAppl();
            }
            if (grupo.contains(appl)) {
                cuenta++;
            }
        }
        return cuenta;
    }
}
