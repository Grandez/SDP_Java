/**
 * Servicio de acceso a la tabla SDP_REL_MOD_APP
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.core;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPAplicacion;
import com.jgg.sdp.domain.core.SDPRelModuloApp;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.tools.Fechas;

@Repository
public class SDPRelModuloAppService extends AbstractService<SDPRelModuloApp> {

	private Configuration cfg = DBConfiguration.getInstance();
	private SDPAplicacionService appService = new SDPAplicacionService();
	
    public SDPRelModuloApp findByName(String mask) {
    	return getRecord(SDPRelModuloApp.findByMask, mask);
    }
    
	public Long getApplication(String moduleName) {
		Long id = getExactMatch(moduleName);
		if (id != null) return id;
		id = getPartialMatch(moduleName);
		if (id != null) return id;
		int len = cfg.getInteger(CFG.AUTO_APP, 0);
		if (len > 0) return createApplication(moduleName.substring(0, len));
		return SYS.APPL_GENERAL;
	}
	
	private long createApplication(String partial) {
        String name = "Generic " + partial;
		
		SDPAplicacion app = appService.findByName(name);
		if (app != null) return app.getId();
		
		app = new SDPAplicacion();

		long idAppl = appService.getNextApplicationId();
		long parent = cfg.getInteger(CFG.APP_BASE);
		app.setId(idAppl);
		app.setPadre(parent);
		app.setAplicacion(name);
		app.setDescripcion(name);
		app.setTms(Fechas.getTimestamp());
		app.setUid(SYS.DEF_USER);
		app.setVolumen(1);
		appService.update(app);
		
		SDPRelModuloApp rel = new SDPRelModuloApp();
		rel.setIdAppl(idAppl);
		rel.setMask(partial + "*");
		rel.setFijo(partial.length());
		rel.setPeso(1000);
		rel.setUid(SYS.DEF_USER);
		update(rel);
		
		return idAppl;
	}
	
	private Long getExactMatch(String moduleName) {
	    SDPRelModuloApp res = findByName(moduleName);
		return (res == null) ? null : res.getIdAppl();
	}
	
	private Long getPartialMatch(String moduleName) {
		
		List<SDPRelModuloApp> lista = listQuery(SDPRelModuloApp.getMasks);
		for (SDPRelModuloApp rel : lista) {
			if (rel.getMask().compareTo("*") != 0) {
			    String p = rel.getMask().replaceAll("\\*",".*");
			    p = p.replaceAll("\\?", ".{1}");
			    if (Pattern.matches(p, moduleName)) return rel.getIdAppl();
			}    
		}
		return null;
	}
}
