/**
 * Servicio de acceso a la tabla CFG_Codigos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.cfg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.cfg.CFGCode;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.core.ctes.SYS;

@Repository
public class CFGCodeService extends AbstractService<CFGCode> {
	
	/**
     * Devuelve la lista de mensajes de un determinado grupo
     *
     * @param grupo El grupo de mensajes a recuperar
     * @param lang  El codigo de idioma a utilizar
     * @return      La lista de mensajes
     */
	public List<CFGCode> getGroupCodes(int grupo, String[] lang) {
		return getGroupCodes(grupo, lang[0], lang[1]);
	}
	
	public List<CFGCode> getGroupCodes(int grupo, String lang, String dialect) {
	    List<CFGCode> aux = listQuery(CFGCode.getGrupo, grupo, lang, dialect);
	    if (aux == null || aux.size() == 0) {
	    	dialect = SYS.DEF_DIALECT;
	    	aux = listQuery(CFGCode.getGrupo, grupo, lang, dialect);
	    }
	    if (aux == null || aux.size() == 0) {
	    	lang = SYS.DEF_LANG;
	    	aux = listQuery(CFGCode.getGrupo, grupo, lang, dialect);
	    }
	    return aux;
	}
	
	/**
     * Obtiene un mensaje identificado por grupo y codigo
     *
     * @param grupo  El grupo  del mensaje
     * @param codigo El codigo del mensaje
     * @param lang   El codigo de idioma a utilizar
     * @return       El mensaje
     */
	public String getMessage(int grupo, int codigo, String[] lang) {
		return getMessage(grupo, codigo, lang[0], lang[1]);
	}
	
	public String getMessage(int grupo, int codigo, String lang, String dialect) {		
	    CFGCode aux = findQuery(CFGCode.getMessage, grupo, codigo, lang, dialect);
		if (aux == null) {
		   	dialect = SYS.DEF_DIALECT;
		   	aux = findQuery(CFGCode.getMessage, grupo, codigo, lang, dialect);
		}
		if (aux == null) {
		    lang = SYS.DEF_LANG;
		    aux = findQuery(CFGCode.getMessage, grupo, codigo, lang, dialect);
		}
		return aux.getData();
	}
}
