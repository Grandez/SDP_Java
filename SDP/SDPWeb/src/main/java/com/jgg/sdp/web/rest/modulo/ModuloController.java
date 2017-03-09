package com.jgg.sdp.web.rest.modulo;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Fechas;
import com.jgg.sdp.domain.module.MODDependencia;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.Modulo;

@RestController
public class ModuloController {

	private Modulo    modulo;
	
    @Autowired
    MODVersionService versionService;
    @Autowired
	MODBloqueService bloqueService;
    @Autowired
	MODResumenService resumenService;
    @Autowired
    MODDependenciaService dependenciaService;
    @Autowired
    MODParrafoService parrafoService;
    @Autowired
    MODGrafoService grafoService;
    
    @Autowired
    SESSesionService sesionService;

    private Configuration cfg = DBConfiguration.getInstance();    
    
    @RequestMapping("/module/{idModulo}")
    public Modulo getModuleInfo(@PathVariable Long idModulo) {
        return getModuleInfo(idModulo, cfg.getInteger(CFG.DATE_INTERVAL));
    }

    @RequestMapping("/module/{idModulo}/{rango}")
    public Modulo getModuleInfo(@PathVariable Long idModulo, @PathVariable Integer rango) {
    	modulo = new Modulo();
    	MODVersion version = getVersion(idModulo);
    	if (version == null) return modulo;

        Timestamp inicio = Fechas.calculaInicio(rango);

        modulo.setExist(true);        
        modulo.setIdModulo(idModulo);
        modulo.setIdVersion(version.getIdVersion());


    	modulo.setVersion(version);
    	modulo.setResumen(resumenService.getResumen(version.getIdVersion()));
    	calculaCobertura(version);
    	calculaEjecuciones(version, inicio);
    	cargaDependencias(version);
    	cargaParrafos(version);
    	cargaGrafo(version);
    	return modulo;

    }

    private MODVersion getVersion(Long idModule) {
    	List<MODVersion> versiones = versionService.getVersionesPorModulo(idModule);
    	if (versiones.size() == 0) return null;
    	return versiones.get(0);
    }

    private void calculaCobertura(MODVersion version) {
        Long ejecutado = bloqueService.getSentenciasEjecutadas(version.getIdVersion());
        modulo.setCobertura(0L);
        if (ejecutado != null) {
            modulo.setCobertura(ejecutado * 100 / modulo.getResumen().getSentencias());
        }    
    }

    private void calculaEjecuciones(MODVersion version, Timestamp from) {
        Object[] datos = sesionService.getTotalEjecucionesByVersion(version.getIdVersion(), from);
        modulo.setEjecuciones((Long) datos[0]);
        modulo.setLastSesion((Timestamp) datos[1]);
    }
    
    private void cargaDependencias(MODVersion version) {
        ArrayList<MODDependencia> deps = modulo.getDependencias();
        List<MODDependencia> lista = dependenciaService.getDependencias(version.getIdVersion());
        for (MODDependencia item : lista) {
            deps.add(item);
            switch (item.getTipo() / 10) {
                case CDG.DEP_COPY:   modulo.incDepCopy(); break;
                case CDG.DEP_MODULO: modulo.incDepModulo(); break;
            }
            
        }
    }
    
    private void cargaParrafos(MODVersion version) {
         modulo.setParrafos(parrafoService.getParrafosByIndex(version.getIdVersion()));        
    }
    
    private void cargaGrafo(MODVersion version) {
           modulo.setGrafo(grafoService.getGrafo(version.getIdVersion(), 1L)); 
    }
    
}
