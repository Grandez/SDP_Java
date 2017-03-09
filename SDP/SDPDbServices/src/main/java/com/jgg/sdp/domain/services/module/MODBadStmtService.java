/**
 * Servicio de acceso a la tabla MOD_BAD_STMT
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODBadStmt;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODBadStmtService extends AbstractService<MODBadStmt> {

   public List<MODBadStmt> getBadStmts(Long idVersion) {
       return list("list", idVersion);
   }

}
