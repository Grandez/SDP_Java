/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.summary.SUMArbolService;
import com.jgg.sdp.domain.services.traps.*;
import com.jgg.sdp.web.json.ModStats;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class StatsController {
    
    @Autowired
    SDPModuloService modService;
    
    @Autowired
    TRPSesionService trapService;
    
    @Autowired
    SESSesionService sesService;
    @Autowired
    SESModuloService sesModService;
    @Autowired
    SESIOService sesIOService;
    @Autowired
    SUMArbolService arbolService;
    
    @RequestMapping("/stats/{idModulo}/{rango}")
    public ModStats getModuleStats(@PathVariable Long idModulo, @PathVariable Integer rango) {
        ModStats stats = new ModStats();
        Timestamp inicio = Fechas.calculaInicio(rango);
        Long idVersion = modService.getCurrentVersion(idModulo);

         getEjecuciones(stats, idVersion, inicio);         
         getTiempos    (stats, idVersion, inicio);
         getIO         (stats, idVersion, inicio);
         getArbol      (stats, idVersion, inicio);
        return stats;
    }
    
    private void getEjecuciones(ModStats stats, Long idVersion, Timestamp inicio) {
        stats.setFallidas(trapService.listFallidas(inicio));
        stats.setEjecuciones(sesService.getCuentaSesionesModulo(idVersion, inicio));
    }
    
    private void getTiempos(ModStats stats, Long idVersion, Timestamp inicio) {
        List<Object[]> tuplas = sesModService.getResumenEjecucionesModulo(idVersion, inicio);
        if (tuplas.size() == 0) return;
        Object[] tupla = tuplas.get(0);
        Long veces = (Long) tupla[0];
        
        if (veces == 0) return;
        
        stats.setElapsedTime((Long) tupla[1] / veces);
        stats.setElapsedMin ((Long) tupla[3]);
        stats.setElapsedMax ((Long) tupla[5]);
        stats.setCpuTime    ((Long) tupla[2] / veces);
        stats.setCpuMin     ((Long) tupla[4]);
        stats.setCpuMax     ((Long) tupla[6]);
    }

    private void getIO(ModStats stats, Long idVersion, Timestamp inicio) {
        Long total = 0L;
        List<Object[]> tuplas = sesIOService.getIOModulo(idVersion, inicio);
        
        if (tuplas.size() == 0) return;
        
        Object[] tupla = tuplas.get(0);
        Long veces = (Long) tupla[0];
        
        if (veces == 0) return;
        
        stats.setInput ((Long) tupla[3] / veces);
        stats.setOutput((Long) tupla[4] / veces);
        
        for (int idx = 1; idx < 8; idx++) total += (Long) tupla[idx];
        
        stats.setIoTotal(total / (Long) tupla[0]);
    }

    private void getArbol(ModStats stats, Long idVersion, Timestamp inicio) {
        int rutinas = 0;
        long total = 1;
        long sesiones = 0;
        List<Object[]> llamadas = arbolService.getCountCalled(idVersion);
        
        for (Object[] llamada : llamadas) {
            rutinas++;
            sesiones += ((BigDecimal) llamada[1]).longValue() * ((BigDecimal) llamada[2]).longValue(); 
            total    *= ((BigDecimal) llamada[2]).longValue();
        }
        
        stats.setArbolModulos(rutinas);
        stats.setArbolRutinas((int) (sesiones * 10 / total));
    }
    
}
