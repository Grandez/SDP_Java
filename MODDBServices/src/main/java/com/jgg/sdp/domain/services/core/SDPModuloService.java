/**
 * Servicio de acceso a la tabla SDP_Modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.core;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPModuloService extends AbstractService<SDPModulo> {

    public SDPModulo findById(Long idModulo) {
        SDPModulo value = dbCache.get(idModulo);
        return (value == null) ? dbCache.put(idModulo, findQuery(SDPModulo.findById, idModulo)) : value;
	}

    public List<SDPModulo> listModulesByAppl(Long idAppl) {
    	return listQuery(SDPModulo.listByAppl, idAppl);
    }
    
	public SDPModulo findByName(String name) {
		return find("findByName", name);
	}

	public SDPModulo findByVersion(Long version) {
		return findQuery(SDPModulo.findByVersion, version);
	}

	public SDPModulo findByNameAndType(String name, Integer type) {
		return findQuery(SDPModulo.findByNameAndType, name, type);
	}
	
	public List<SDPModulo> getModulesByPseudoMask(String mask) {
	    return list("ModulosPorPseudoMascara", mask + "%"); 	
	}
	
	public List<SDPModulo> getModulosActivos() {
        return list("ModulosActivos");  
    }
	
	public Long getCuentaModulos(Long idAppl) {
	    return getValue("CuentaPorAplicacion", idAppl);
	}
	
	public void updateVersion(Long idModulo, Long idVersion) {
	    updateQuery("updateVersion", idModulo, idVersion);
	}
	
    public List<SDPModulo> getModulosModificados(Timestamp tms) {
        return list("ModulosModificados", tms);
    }

    public Long getCurrentVersion(Long idModulo) {
        SDPModulo value = dbCache.get(idModulo);
        if (value == null) value = findById(idModulo);
        return value.getIdVersion();
    }
    
}
