/**
 * Servicio de acceso a la tabla MOD_Fichero
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODFichero;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODFicheroService extends AbstractService<MODFichero> {

	public MODFichero[] getFiles(Long idVersion) {
		List<MODFichero> datos = list("list", idVersion);
		return datos.toArray(new MODFichero[datos.size()]);
	}
	
	public List<MODFichero> getMaestros(Long idVersion) {
		return getList(MODFichero.getMaestros, idVersion);
	}
	
	public MODFichero getByIndex(Long idVersion, Integer idFile) {
		return getRecord(MODFichero.getByIndex, idVersion, idFile);
	}

}
