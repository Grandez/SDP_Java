/**
 * Servicio de acceso a la tabla LOG_MSG 
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.log;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.log.LOGMsg;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class LOGMsgService extends AbstractService<LOGMsg> {

    public String getMessage(int code, String lang) {
        LOGMsg l = find("find", code, lang);
        if (l == null) l = find("find", code, "XX");
        if (l == null) return null;
        return l.getMsg();
    }

    /**
     * Devuelve la lista de mensajes asociados a un grupo
     * Tomando como base el codiog indicado
     * Va recuperando mensajes incrementando el codigo hasta que 
     * no existe el mensaje
     *
     * @param code Codigo inicial
     * @param lang Codigo de idioma a utilizar
     * @return     La lsita de mensajes
     */
    
    public List<String> getListOfMessages(int code, String lang) {
        List<String> lista = new ArrayList<String>();
        String aux = getMessage(code, lang);
        while (aux != null) {
            lista.add(aux);
            code++;
            aux = getMessage(code, lang);
        }
        return lista;
    }
}
