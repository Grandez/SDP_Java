/**
 * Servicios de escritura en los tablas de Log
 * <br>
 * Son servicios enriquecidos para abstraer la complejidad
 * de la escritura de los logs a los programas que lo consumen
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.log;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.log.LOGLogging;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.core.ctes.*;

@Repository
public class LOGInputService extends AbstractService<LOGLogging> {

    private SDPModuloService  moduloService  = new SDPModuloService();
    
    public boolean registra(int code, Object... parms) {
    	return false;
    	/*JGG
        switch (code) {
           case LOG.MOD_NEW:     return modNew(code, parms);
           case LOG.MOD_COMP: 
           case LOG.MOD_VERSION: return modComp(code, parms);
           case LOG.SES_CPU:
           case LOG.SES_ELAPSED:
           case LOG.SES_SUSPEND: return sesLog(code, parms);    
        }   
        return false;
        */
    }

    public boolean logWeb(int code, long modulo, String objeto, String user, Object...parms) {
        LOGLogging l = new LOGLogging();
        l.setIdAppl(SYS.APPL_WEB);
        l.setIdModulo(modulo);
        l.setIdVersion(0L);
        l.setIdMsg(code);
        l.setIdTipo(LOG.INFO);
        l.setTms(new Timestamp(System.currentTimeMillis()));
        for (int idx = 0; idx < parms.length; idx++) l.setDato(idx, (String) parms[idx]); 
        l.setUid(user);
        l.setObjeto(objeto);
        update(l);
        return false;
        
    }
    private boolean modNew(int code, Object... parms) {
        LOGLogging l = new LOGLogging();
        SDPModulo m = (SDPModulo) parms[0];
        l.setIdAppl(m.getIdAppl());
        l.setIdModulo(m.getIdModulo());
        l.setIdVersion(0L);
        l.setIdMsg(code);
        l.setIdTipo(1);
        l.setTms(m.getTms());
        l.setDato(0, m.getNombre());
        l.setUid(m.getUid());
        l.setObjeto(m.getNombre());
        update(l);
        return false;
    }

    private boolean modComp(int code, Object... parms) {
        LOGLogging l = new LOGLogging();
        MODVersion v = (MODVersion) parms[0];
        SDPModulo m = moduloService.findById(v.getIdModulo());
        
        l.setIdAppl(m == null ? 0L : m.getIdAppl());
        l.setIdModulo(v.getIdModulo());
        l.setIdVersion(v.getIdVersion());
        l.setIdMsg(code);
        l.setIdTipo(LOG.INFO);
        l.setTms(v.getTms());
        l.setDato(0, v.getNombre());
        l.setUid(v.getUid());
        l.setObjeto(v.getNombre());
        update(l);
        return false;
    }

    private boolean sesLog(int code, Object... parms) {
        LOGData data = (LOGData) parms[0];
        LOGLogging l = new LOGLogging();
        l.setIdAppl(data.getIdAppl());
        l.setIdModulo(data.getIdModulo());
        l.setIdVersion(data.getIdVersion());
        l.setIdMsg(code);
        l.setIdTipo(LOG.ALERT);
        l.setUid(data.getUid());
        l.setTms(data.getTms());
        l.setDato(0, data.getParm(0));

        l.setObjeto(data.getNombre());
        update(l);
        return false;

    }
}
