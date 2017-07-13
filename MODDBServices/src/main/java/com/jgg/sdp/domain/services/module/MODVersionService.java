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

    public MODVersion findById(Long idVersion) {
    	return findQuery(MODVersion.findById, idVersion);
    }
	    
    public List<MODVersion> getVersionesPorTimestamp(Long id, Timestamp tms) {
	      return listQuery (MODVersion.versionesByTimestamp,  id, tms); 	
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
    
    public void deleteByTimestamp(Timestamp tms) {
    	deleteQuery(MODVersion.deleteByTimestamp, tms);
    }
}
