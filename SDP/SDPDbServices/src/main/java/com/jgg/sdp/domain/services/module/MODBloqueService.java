/**
 * Servicio de acceso a la tabla MOD_Bloque
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODBloque;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODBloqueService extends AbstractService<MODBloque> {

   public MODBloque find(Long idVersion, Integer orden) {
	   return getRecord(MODBloque.findByKey, idVersion, orden);
   }
   
	public MODBloque[] getBloques(Long idVersion) {
		List<MODBloque> datos = list("list", idVersion);
		return datos.toArray(new MODBloque[datos.size()]);
	}   
	
	public Long getSentenciasEjecutadas(Long idVersion) {
	    List<Object[]> res = getListAbstract(MODBloque.totStmt, idVersion);
	    return (Long) res.get(0)[0];
	}
}
