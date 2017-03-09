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

import com.jgg.sdp.domain.core.SDPRelModuloApp;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.core.ctes.SYS;

@Repository
public class SDPRelModuloAppService extends AbstractService<SDPRelModuloApp> {

    public SDPRelModuloApp findByName(String mask) {
    	return getRecord(SDPRelModuloApp.findByMask, mask);
    }
    
	public Long getApplication(String moduleName) {
		Long id = getExactMatch(moduleName);
		if (id != null) return id;
		id = getPartialMatch(moduleName);
		return (id == null) ? 0L : id;
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
