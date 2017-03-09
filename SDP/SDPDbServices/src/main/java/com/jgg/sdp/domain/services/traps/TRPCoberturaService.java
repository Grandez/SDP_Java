/**
 * Servicio de acceso a la tabla TRP_Cobertura
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPCobertura;

@Repository
public class TRPCoberturaService extends AbstractService<TRPCobertura> {

    public List<TRPCobertura> getCobertura(String idSesion, String firmaModulo) {
    	return list("list", idSesion, firmaModulo);
    }
}
