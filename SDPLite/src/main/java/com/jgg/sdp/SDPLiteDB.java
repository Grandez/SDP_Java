/**
 * 
 * Actua de wrapper para acceder a la base de datos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */

package com.jgg.sdp;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.services.core.SDPFilesService;
import com.jgg.sdp.module.unit.Source;

public class SDPLiteDB {

	private Configuration cfg = Configuration.getInstance();
	    	
    private SDPFilesService   fileService    = new SDPFilesService();
    
    	/**
	 * Verifica si la unidad existe y ha sido procesada
	 * Si se ha especificado FORCE entonces obliga a ser procesada
	 * @param archivo
	 * @return
	 */
	
	public int hasToProcess(Source source) {
		if (cfg.getBoolean(CFG.PARSER_FORCE)) return MSG.OK; 

		String name = source.getBaseName();

		SDPFile f = fileService.findByNameAndType(name, CDG.SOURCE_CODE);
		if (f == null) return MSG.OK;
		if (f.getFirma().compareTo(source.getFirma()) == 0) return MSG.SKIP;
		return MSG.OK;
	}


}
