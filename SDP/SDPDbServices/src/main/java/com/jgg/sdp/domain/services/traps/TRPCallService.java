/**
 * Servicio de acceso a la tabla TRP_Call
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPCall;

@Repository
public class TRPCallService extends AbstractService<TRPCall> {

	public List<TRPCall> getListaCalls(String sesion) {
		return list("listCalls" , sesion);
	}

	public List<Object[]> getSumCalls(String sesion) {
	    return nativeQuery(TRPCall.sumCalls, sesion);
	}
}
