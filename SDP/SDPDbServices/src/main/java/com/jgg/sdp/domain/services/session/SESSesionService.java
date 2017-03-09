/**
 * Servicio de acceso a la tabla SES_Sesion
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.session;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.session.SESSesion;

@Repository
public class SESSesionService extends AbstractService<SESSesion> {

	public SESSesion findBySession(String sesion) {
		return getRecord(SESSesion.find, sesion);
	}
	
	public List<SESSesion> getSesionesByTimestamp(Timestamp from) {
	    return list("sesiones", from);
	}
	
	public Object[] getTotalEjecucionesByVersion(Long idVersion, Timestamp from) {
	    List<Object[]> res = getListAbstract(SESSesion.totSesiones, idVersion, from);
	    return res.get(0);
	}

    public List<SESSesion> getSesionesByVersion(Long idVersion) {
        return list("sesionesByVersion", idVersion, new Timestamp(0L));
    }
    
    public List<SESSesion> getSesionesByElapsed(Long idVersion, Timestamp from) {
        return list("sesionesByElapsed", idVersion, from);
    }
	
	public List<SESSesion> getSesionesByVersion(Long idVersion, Timestamp tms) {
        return list("sesionesByVersion", idVersion, tms);
	}
	
	public List<Object[]> getCuentaSesiones(Timestamp tms) {
	    return listAbstract("cuentaSesiones", tms);
	}
    
    public Long getCuentaSesionesModulo(Long idVersion, Timestamp tms) {
        return getValue("cuentaSesionesModulo", idVersion,  tms);
    }
	
	public List<SESSesion> getFallidos(Timestamp tms) {
	    return list("fallidas", tms);
	}
	
	public List<Object[]> getTotalSesionesByVersion(Timestamp tms) {
	    return getListAbstract(SESSesion.sesionesPorVersion, tms);
	}
}
