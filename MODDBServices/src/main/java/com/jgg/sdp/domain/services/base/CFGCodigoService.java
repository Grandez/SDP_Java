/**
 * Servicio de acceso a la tabla CFG_Codigos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.base;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.base.CFGCodigo;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.core.ctes.SYS;

@Repository
public class CFGCodigoService extends AbstractService<CFGCodigo> {
	
	/**
     * Devuelve la lista de mensajes de un determinado grupo
     *
     * @param grupo El grupo de mensajes a recuperar
     * @param lang  El codigo de idioma a utilizar
     * @return      La lista de mensajes
     */
	public List<CFGCodigo> getCodigos(int grupo, String lang) {
	    List<CFGCodigo> aux = list("getGrupo", grupo, lang);
	    if (lang.compareTo(SYS.DEF_LANG) == 0) return aux;
	    if (aux.size() == 0) aux = list("getGrupo", grupo, SYS.DEF_LANG);
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
	public String getMessage(int grupo, int codigo, String lang) {
	    CFGCodigo      c = find("getMessage", grupo, codigo, lang);
	    if (c == null) c = find("getMessage", grupo, codigo, SYS.DEF_LANG);
	    return (c == null) ? SYS.NULL : c.getValor();
	}
}
