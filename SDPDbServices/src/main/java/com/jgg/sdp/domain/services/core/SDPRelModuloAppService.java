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
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.core.tools.Fechas;

@Repository
public class SDPRelModuloAppService extends AbstractService<SDPRelModuloApp> {

	private Configuration cfg = Configuration.getInstance();
	
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
		return (id == null) ? 0L : id;
	}
	
	private long createApplication(String partial) {
		SDPAplicacion app = new SDPAplicacion();
		SDPAplicacionService appService = new SDPAplicacionService();
		long idAppl = appService.getNextApplicationId();
		long parent = cfg.getInteger(CFG.APP_BASE);
		app.setId(idAppl);
		app.setPadre(parent);
		app.setAplicacion("Generic " + partial);
		app.setDescripcion("Generic " + partial);
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
		
		List<SDPRelModuloApp> lista = getList(SDPRelModuloApp.getMasks);
		for (SDPRelModuloApp rel : lista) {
			String p = rel.getMask().replaceAll("\\*",".*");
			p = p.replaceAll("\\?", ".{1}");
			if (Pattern.matches(p, moduleName)) return rel.getIdAppl();
		}
		return SYS.APPL_GENERAL;
	}
}
