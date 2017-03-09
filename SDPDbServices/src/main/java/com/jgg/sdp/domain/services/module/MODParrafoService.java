/**
 * Servicio de acceso a la tabla MOD_Parrafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODParrafo;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODParrafoService extends AbstractService<MODParrafo> {

	List<MODParrafo> lstParrafos = null;

	public MODParrafo getParrafo() {
		MODParrafo aux = null;
		if (lstParrafos == null) return null;
		if (lstParrafos.size() == 0) {
			lstParrafos = null;
			return null;
		}
		
		aux = lstParrafos.get(0);
		lstParrafos.remove(0);
		return aux;
   }

	public List<MODParrafo> getParrafosByIndex(Long idVersion) {
		return list("listByIndex", idVersion); 
	}
    
    public List<MODParrafo> getParrafosByName(Long idVersion) {
        return list("listByName", idVersion); 
    }

}
