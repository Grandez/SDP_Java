/**
 * Servicio de acceso a la tabla MOD_Version
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODVersionService extends AbstractService<MODVersion> {

    public MODVersion getByFirma(String firma) {
    	return getRecord(MODVersion.findByFirma, firma);
    }
	
    public MODVersion findByFirma(Long idModulo, String firma) {
    	return find("findByFirma", idModulo, firma);
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

    public List<Long> getListOfModulesWithVersionsExceeded(int max) {
        ArrayList<Long> lista = new ArrayList<Long>();
        for (Object[] o : getListAbstract(MODVersion.cuentaDeVersiones)) {
        	if (((Long) o[1]) > max) {
        		lista.add((Long) o[0]);
        	}
        }
        return lista;
    }
    
    public List<Timestamp> getTimestamps(Long idModulo) {
        ArrayList<Timestamp> lista = new ArrayList<Timestamp>();
        for (Object[] o : getListAbstract(MODVersion.versionesByTimestamp, idModulo)) {
       		lista.add((Timestamp) o[1]);
        }
        return lista;
        	
    }
    
    public void deleteByTimestamp(Timestamp tms) {
    	deleteQuery(MODVersion.deleteByTimestamp, tms);
    }
}
