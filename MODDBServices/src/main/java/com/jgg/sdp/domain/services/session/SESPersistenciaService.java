/**
 * Servicio de acceso a la tabla SES_Persistencia
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.session;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.session.SESPersistencia;

@Repository
public class SESPersistenciaService extends AbstractService<SESPersistencia> {

	public ArrayList<SESPersistencia> getFilesBySesion(String idSesion) {
		return (ArrayList<SESPersistencia>) list("filesBySesion", idSesion);
	}

	public ArrayList<SESPersistencia> getTotalFilesBySesion(String idSesion) {
		return (ArrayList<SESPersistencia>) list("totalFilesBySesion", idSesion);
	}
	
	public SESPersistencia getTotalFileById(String idSesion, Long idVersion, Integer idFile) {
		return find("totalFileById", idSesion, idVersion, idFile);
	}
	
	public void updateMaestro(Long idVersion, Integer idFile, Integer maestro) {
		updateQuery("updMaestro", idVersion, idFile, maestro);
	}
	
	public Object[] getTotalMaestros(String idSesion) {
		List<Object[]> data = getListAbstract(SESPersistencia.sumTotalMaestros, idSesion);

		return data.get(0);
	}
	
	public Long getMaxLeido(String idSesion) {
	     return getValue("maxLeido", idSesion);
    }

	public Long getMaxEscrito(String idSesion) {
        return getValue("maxEscrito", idSesion);
    }
	
	public List<Object[]> getTotalesByModulo(String idSesion) {
	    return getListAbstract(SESPersistencia.sumByModulo, idSesion);
	}

	public List<Object[]> getTotalesBySesion(String idSesion) {
        return getListAbstract(SESPersistencia.sumBySesion, idSesion);
    }

} 
