/**
 * Servicio de acceso a la tabla SDP_Aplicacion
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.core;

import java.math.BigInteger;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPAplicacion;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPAplicacionService extends AbstractService<SDPAplicacion> {

	
	public SDPAplicacion findById(Long id) {
	    SDPAplicacion value = dbCache.get(id);
	    return (value == null) ? dbCache.put(id, find("find", id)) : value;
	}
	
	 public SDPAplicacion findByName(String name) {
		return findQuery(SDPAplicacion.findByName, name);
	}
	
    public List<SDPAplicacion> listByParent(Long id) {
    	return listQuery(SDPAplicacion.listChildrens, id);
    }
    
	 public List<Long> getTree(Long id) {
		ArrayList<Long> arbol = new ArrayList<Long>();
		
		SDPAplicacion app = findById(id);
		while (app.getPadre() != 0) {
			arbol.add(app.getPadre());
			app = findById(app.getPadre());
		}
		arbol.add(0L);
		return arbol;
	}
	
	public Set<Long> getConjuntoActivo(Long id) {
	    Set<Long> datos = new HashSet<Long>();
	    datos.add(id);
	    cargaHijos(datos, id);
	    return datos;
	}
	
	public void updateVolumen(String appName) {
	    sqlExecute(SDPAplicacion.updVolumen, appName);	    
	}

	public long getNextApplicationId() {
		Long data = (Long) getItemAbstract(SDPAplicacion.maxAppId);
        return data + 1;
		
	}
	
	private void cargaHijos(Set<Long> datos, Long id) {
	    for (Object item : nativeQueryItem(SDPAplicacion.listChildrens, id)) {
	        datos.add(((BigInteger) item).longValue());
	        cargaHijos(datos, ((BigInteger) item).longValue());
	    }
	}
	

}
