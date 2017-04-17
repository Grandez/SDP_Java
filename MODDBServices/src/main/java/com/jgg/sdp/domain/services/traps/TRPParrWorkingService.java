/**
 * Servicio de acceso a la tabla TRP_Parr_Working
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPParrWorking;

@Repository
public class TRPParrWorkingService extends AbstractService<TRPParrWorking> {

    public TRPParrWorking getParrafos(String sesion, String firma, Long menor, Long mayor) {
    	return find("find", sesion, firma, menor, mayor);
    }
    
    public List<TRPParrWorking> getParrafosBySesion(String sesion, String firma) {
        return list("findBySesion", sesion, firma);
    }

}
