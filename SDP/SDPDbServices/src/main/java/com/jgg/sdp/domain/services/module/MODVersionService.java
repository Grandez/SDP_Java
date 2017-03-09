/**
 * Servicio de acceso a la tabla MOD_Version
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODVersionService extends AbstractService<MODVersion> {

    public MODVersion getByFirma(String firma) {
    	return getRecord(MODVersion.findByFirma, firma);
    }

    public MODVersion getByVersion(Long idVersion) {
    	return find("findByVersion", idVersion);
    }
    
    public List<MODVersion> getVersionesPorModulo(Long id) {
	      return list ("VersionesPorModulo", id); 	
	}
    
    public MODVersion getCurrentVersion(Long idModulo) {
        List<MODVersion> l = getVersionesPorModulo(idModulo);
        if (l == null) return null;
        if (l.size() == 0) return null;
        return l.get(0);
    }
}
