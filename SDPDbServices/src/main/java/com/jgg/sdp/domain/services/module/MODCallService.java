/**
 * Servicio de acceso a la tabla MOD_Dependencia
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODCall;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODCallService extends AbstractService<MODCall> {
    
    public List<MODCall> getListCall(Long idVersion) {
          return lista(MODCall.listCall, idVersion);
    }
    
    public List<MODCall> getListCalling(String module) {
        return lista(MODCall.listCalling, module);
   }
 
   public MODCall getModule(Long idVersion, String module) {
	   return findQuery(MODCall.find, idVersion, module);
   }
    
}