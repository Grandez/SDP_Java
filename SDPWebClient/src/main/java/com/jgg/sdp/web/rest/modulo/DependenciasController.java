/**
 * Servicio REST para devolver la información relativa 
 * a las dependencias de un modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.cfg.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.web.json.*;

@RestController
public class DependenciasController {

	@Autowired
	MODVersionService     versionService;
//    @Autowired
//    MODDependenciaService depService;
    @Autowired
    CFGCodeService      cdgService;
    
    @RequestMapping("/deps/{idModule}")
    public List<Dependencia> getCopys(@PathVariable  Long idModule,
                                      @RequestHeader HttpHeaders headers) {
          List<Dependencia> deps = new ArrayList<Dependencia>();
//        
//        String lang = LANG.getLanguage(headers);
//    	
//        MODVersion version = getVersion(idModule);
//    	
//        List<MODDependencia> lista = depService.getDependencias(version.getIdVersion());
//
//        for (MODDependencia dep : lista) {
//            Dependencia d = new Dependencia();
//            d.setTipo(dep.getTipo());
//            d.setSubTipo(dep.getSubTipo());
//            d.setNombre(dep.getModulo());
//            d.setEstado(dep.getEstado());
//            d.setDesc(cdgService.getMessage(CDG.COPYS, d.getSubTipo(), lang));
//            deps.add(d);
//        }
         return deps;
    }

    @RequestMapping("/routines/{idVersion}")
    public List<Dependencia> getModulos(@PathVariable  Long idVersion,
                                      @RequestHeader HttpHeaders headers) {
        List<Dependencia> deps = new ArrayList<Dependencia>();
        
//        String lang = LANG.getLanguage(headers);
//        List<MODDependencia> lista = depService.getRoutines(idVersion);
//
//        for (MODDependencia dep : lista) {
//            Dependencia d = new Dependencia();
//            d.setTipo(dep.getTipo());
//            d.setSubTipo(dep.getSubTipo());
//            d.setNombre(dep.getModulo());
//            d.setEstado(dep.getEstado());
//            //d.setDesc(cdgService.getMessage(CDG.MODULOS, d.getSubTipo(), lang));
//            d.setDesc(cdgService.getMessage(CDG.MODULOS, CDG.CALL_DYNAMIC, lang));
//            deps.add(d);
//        }
        return deps;
    }

    private MODVersion getVersion(Long idModule) {
    	return null;
    	/*
    	List<MODVersion> versiones = versionService.getVersionesPorModulo(idModule);
    	if (versiones.size() == 0) return null;
    	return versiones.get(0);
    	*/
    }

}
