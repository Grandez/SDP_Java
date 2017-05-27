/**
 * Controlador REST para obtener informacion de un modulos
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.rules.RULIssue;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.rules.RULIssuesService;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.TRPSesionService;
import com.jgg.sdp.domain.traps.TRPSesion;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.modulo.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class ModuloController {

	private Modulo    modulo;
	
    @Autowired
    MODVersionService     versionService;
    @Autowired
	MODBloqueService      bloqueService;
    @Autowired
	MODResumenService     resumenService;
    @Autowired
    MODDependenciaService dependenciaService;
    @Autowired
    MODParrafoService     parrafoService;
    @Autowired
    MODGrafoService       grafoService;
    @Autowired
    MODBadStmtService     badService;
    @Autowired
    TRPSesionService      trpService;

    @Autowired
    RULIssuesService      issueService;
    
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
    	Resumen resumen = new Resumen();
    	
    	MODVersion version = getVersion(idModulo);
    	if (version == null) return modulo;

        Timestamp inicio = Fechas.calculaInicio(rango);

        modulo.setExist(true);        
        modulo.setIdModulo(idModulo);
        modulo.setIdVersion(version.getIdVersion());
        
        modulo.setNombre(version.getNombre());
        modulo.setDesc(version.getDesc());
        
        
        // Atributos
        Attrs attrs = new Attrs();
        attrs.setFull(version.hasFull());
        attrs.setCics(version.hasCICS());
        attrs.setSgdb(version.hasSQL());
        attrs.setFile(version.hasFile());
        attrs.setCall(version.hasCall());
        
        modulo.setAttrs(attrs);
        
        modulo.setMaxCCParr(cfg.getInteger(CFG.CODE_CC_PARR, SYS.DEF_CC));
        modulo.setMaxCC    (cfg.getInteger(CFG.CODE_CC, SYS.DEF_CC));
        modulo.setMaxStmt  (cfg.getInteger(CFG.CODE_STMT, SYS.DEF_STMT));
        
        calculaResumen(resumen, version);
        modulo.setResumen(resumen);
        
    	calculaCobertura(version);
    	calculaEjecuciones(version, inicio);
    	cargaParrafos(version);
    	cargaGrafo(version);
    	return modulo;

    }

    private MODVersion getVersion(Long idModule) {
    	List<MODVersion> versiones = versionService.getVersionesPorModulo(idModule);
    	if (versiones.size() == 0) return null;
    	return versiones.get(0);
    }

    private void calculaResumen(Resumen resumen, MODVersion version) {
        MODResumen mod = resumenService.getResumen(version.getIdVersion());
        resumen.setInfo(mod);
        
        resumen.setUid(version.getUid());
        resumen.setLastCompile(version.getTms());
        resumen.setNombre(version.getNombre());
        resumen.setLineas(mod.getLineas());
        resumen.setSentencias(mod.getSentencias());
        resumen.setParrafos(mod.getParrafos());
        
        obtieneSentenciasErroneas(resumen, version.getIdVersion());
        cargaDependencias(resumen, version.getIdVersion());
    }

    private void calculaCobertura(MODVersion version) { 
        Long ejecutado = bloqueService.getSentenciasEjecutadas(version.getIdVersion());
        modulo.setCobertura(0L);
        if (ejecutado != null) {
            modulo.setCobertura(ejecutado * 100 / modulo.getResumen().getSentencias());
        }    
        RULIssue issue = issueService.getIssue(ISSUE.MOD_COVERAGE);
        Integer valor = Integer.parseInt(issue.getValor());
        modulo.setMaxCobertura(0L + valor);
        if (valor > modulo.getCobertura()) modulo.setStatus(SYS.STATUS_KO);
    }

    private void calculaEjecuciones(MODVersion version, Timestamp from) {
        Object[] datos = sesionService.getTotalEjecucionesByVersion(version.getIdVersion(), from);
        Resumen res = modulo.getResumen();
        res.setLastSesion((Timestamp) datos[1]);
        long fallos = getFallidas(version.getFirma(), from);
        res.setEjecuciones(((Long) datos[0]) + fallos);
    }
    
    private long getFallidas(String firma, Timestamp from) {
        List<TRPSesion> lista = trpService.listFallidasPorModulo(firma, from);
        return lista.size();
    }
    
    private void cargaDependencias(Resumen res, Long idVersion) {
        List<MODDependencia> lista = dependenciaService.getDependencias(idVersion);
        for (MODDependencia item : lista) {
            switch (item.getTipo()) {
                case CDG.DEP_COPY:   res.incDepCopy(); break;
                case CDG.DEP_MODULE: res.incDepModulo(); break;
            }
            
        }
    }
    
    private void obtieneSentenciasErroneas(Resumen resumen, Long idVersion) {
        List<MODBadStmt> lista = badService.getBadStmts(idVersion);
        resumen.setBadStmts(lista.size());
    }
    
    private void cargaParrafos(MODVersion version) {
         modulo.setParrafos(parrafoService.getParrafosByIndex(version.getIdVersion()));        
    }
    
    private void cargaGrafo(MODVersion version) {
           modulo.setGrafo(grafoService.getGrafo(version.getIdVersion(), 1L)); 
    }
    
}
