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

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;

import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;

import com.jgg.sdp.domain.rules.RULIssue;


import com.jgg.sdp.domain.services.rules.RULIssuesService;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.domain.services.traps.TRPSesionService;
import com.jgg.sdp.domain.traps.TRPSesion;
import com.jgg.sdp.tools.Fechas;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.modulo.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;

@RestController
public class ModuloController {

	private Modulo    modulo;

    @Autowired
    SDPFilesService      fileService;	
    @Autowired
    SDPModuloService     modService;
    @Autowired
    MODVersionService    verService;
    @Autowired
    MODCodigoService     cdgService;
    @Autowired
    MODSummaryService    sumService;

    @Autowired
	MODBloqueService      bloqueService;
    
//    @Autowired
//    MODDependenciaService dependenciaService;
    @Autowired
    MODParrafoService     parrafoService;
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

    	SDPModulo mod = modService.findById(idModulo);
    	if (mod == null) return modulo;

    	modulo.setIdModulo(mod.getIdModulo());
    	modulo.setNombre(mod.getNombre());
    	
    	MODVersion version = verService.findById(mod.getIdVersion());
    	if (version == null) return modulo;
        modulo.setIdVersion(version.getIdVersion());
        modulo.setDesc(version.getDesc());
        
        Timestamp inicio = Fechas.calculaInicio(rango);

        modulo.setSummary(calculaSummary(mod, version));
        modulo.setVersiones(calculaVersiones(mod, inicio));
        modulo.getSummary().setVersions(modulo.getVersiones().size());
        
    	MODCodigo cdg = cdgService.find(version.getIdVersion());
    	modulo.setComment(calculaComentarios(cdg));
    	modulo.setVerbs(calculaVerbos(cdg));
        modulo.setAttrs(calculaAtributos(version.getIdVersion()));
        
/*        
    	Resumen resumen = new Resumen();
    	

    	
        modulo.setExist(true);        
        modulo.setIdModulo(idModulo);
        modulo.setIdVersion(version.getIdVersion());
        
        modulo.setVersiones(calculaVersiones(mod, inicio));

        
        obtieneCodigo(modulo, version.getIdVersion());
        
        modulo.setNombre(version.getNombre());
        modulo.setDesc(version.getDesc());
        
        modulo.setMaxCCParr(cfg.getInteger(CFG.CODE_CC_PARR, SYS.DEF_CC));
        modulo.setMaxCC    (cfg.getInteger(CFG.CODE_CC, SYS.DEF_CC));
        modulo.setMaxStmt  (cfg.getInteger(CFG.CODE_STMT, SYS.DEF_STMT));
        
        calculaResumen(resumen, version);
        modulo.setResumen(resumen);
        
//    	calculaCobertura(version);
//    	calculaEjecuciones(version, inicio);
//    	cargaParrafos(version);
//    	cargaGrafo(version);
 
 */
    	return modulo;

    }

    
    private Summary calculaSummary(SDPModulo mod, MODVersion ver) {
    	Summary summ = new Summary();
    	SDPFile file = fileService.findById(ver.getIdFile());
    	summ.setFileName(file == null ? "" : file.getArchivo());
    	summ.setMemberName(mod.getNombre());
    	summ.setAuthor(ver.getAuthor());
    	summ.setUid(ver.getUid());
    	summ.setTms(ver.getTms().getTime());
    	return summ;
    }
    
    private List<Version> calculaVersiones(SDPModulo mod, Timestamp inicio) {
    	List<MODVersion> versiones = verService.getVersionesPorTimestamp(mod.getIdModulo(), inicio);
    	ArrayList<Version> lst = new ArrayList<Version>();
    	int current = mod.getVersiones();
    	for (MODVersion version : versiones) {
    		Version v = new Version();
    		v.setId(version.getIdVersion());
    		v.setOrden(current--);
    		v.setFecha(version.getTms());
    		v.setUid(version.getUid());
    		lst.add(v);
    	}
    	return lst;
    }

    private Comment calculaComentarios(MODCodigo cdg) {   	
    	Comment cmt = new Comment();
    	cmt.setBlancos(cdg.getBlancos());
    	cmt.setBloques(cdg.getBloques());
    	cmt.setComentarios(cdg.getComentarios());
    	cmt.setDecoradores(cdg.getDecoradores());
    	return cmt;
    }
    
    private Verbs calculaVerbos(MODCodigo cdg) {
    	Verbs verbo = new Verbs();
    	verbo.setArit(cdg.getVerbosArit());
    	verbo.setCics(cdg.getVerbosCics());
    	verbo.setCtrl(cdg.getVerbosControl());
    	verbo.setData(cdg.getVerbosData());
    	verbo.setFlow(cdg.getVerbosFlujo());
    	verbo.setIo(cdg.getVerbosIO());
    	verbo.setLang(cdg.getVerbosLang());
    	verbo.setSql(cdg.getVerbosSql());
    	verbo.calculateTotal();
    	return verbo;
    }
    

    private Attrs calculaAtributos(long idVersion) {
    	MODSummary summ = sumService.find(idVersion);

    	Attrs attrs = new Attrs();
        attrs.setCics(summ.hasCics());
        attrs.setSgdb(summ.hasSgdb());
        attrs.setFile(summ.hasFichero());
        attrs.setCall(summ.getCallsCount() > 0 ? true : false);
        return attrs;
    }
    
/*    
    private void calculaResumen(Resumen resumen, MODVersion version) {
    	
//        MODResumen mod = resumenService.getResumen(version.getIdVersion());
//        resumen.setInfo(mod);
//        
//        resumen.setUid(version.getUid());
//        resumen.setLastCompile(version.getTms());
//        resumen.setNombre(version.getNombre());
//        resumen.setLineas(mod.getLineas());
//        resumen.setSentencias(mod.getSentencias());
//        resumen.setParrafos(mod.getParrafos());
//        
//        cargaDependencias(resumen, version.getIdVersion());
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
//        long fallos = getFallidas(version.getFirma(), from);
//        res.setEjecuciones(((Long) datos[0]) + fallos);
    }
    
    private long getFallidas(String firma, Timestamp from) {
        List<TRPSesion> lista = trpService.listFallidasPorModulo(firma, from);
        return lista.size();
    }
    
    private void cargaDependencias(Resumen res, Long idVersion) {
//        List<MODDependencia> lista = dependenciaService.getDependencias(idVersion);
//        for (MODDependencia item : lista) {
//            switch (item.getTipo()) {
//                case CDG.DEP_COPY:   res.incDepCopy(); break;
//                case CDG.DEP_MODULE: res.incDepModulo(); break;
//            }
//            
//        }
    }
        
    private void cargaParrafos(MODVersion version) {
         modulo.setParrafos(parrafoService.getParrafosByIndex(version.getIdVersion()));        
    }
  */  
}
