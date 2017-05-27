/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.log.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.*;
import com.jgg.sdp.web.core.*;
import com.jgg.sdp.web.json.Log;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class LogController {

    @Autowired
    private SDPAplicacionService appService;
    
    @Autowired
    LOGOutputService logService;

    @Autowired
    LOGMsgService msgService;
    
    private Configuration cfg = DBConfiguration.getInstance();

    /**
     * Gets the log.
     *
     * @param id
     *            the id
     * @param rango
     *            the rango
     * @param headers
     *            the headers
     * @param idMsg
     *            the id msg
     * @param object
     *            the object
     * @param tms
     *            the tms
     * @param uid
     *            the uid
     * @return the log
     */
    @RequestMapping("/log/{id}/{rango}")
    public List<Log> getLog(@PathVariable Long id, 
                            @PathVariable Integer rango,  
                            @RequestHeader HttpHeaders headers,
                            @RequestParam(value="idMsg" , required=false) String idMsg,
                            @RequestParam(value="object", required=false) String object,
                            @RequestParam(value="tms",    required=false) Long   tms,
                            @RequestParam(value="uid",    required=false) String uid) {
        boolean hasFilter = false;
        Object[] filtros = new Object[4];
        
        int maxRecords = cfg.getInteger(CFG.LOG_RECORDS);
        
        List<Log> lista = new ArrayList<Log>();
        Set<Long> grupo = appService.getConjuntoActivo(id);
        String lang = LANG.getLanguage(headers);
        Timestamp from = Fechas.calculaInicio(rango);

        hasFilter = aplicaFiltros(filtros, idMsg, object, tms, uid);
        
        List<LOGLogging> lstRecords = (hasFilter) ? logService.listWithFilter(from, filtros)
                                                  : logService.list(from, maxRecords);
        
        for (LOGLogging l : lstRecords) {
            if (grupo.contains(l.getIdAppl())) {
                Log log = new Log();
                log.setLog(l);
                log.setMsg(montaMensaje(l, lang));
                char c = 'U';
                switch (l.getIdTipo()) {
                   case LOG.INFO:    c = 'I'; break;
                   case LOG.WARNING: c = 'W'; break;
                   case LOG.ALERT:   c = 'A'; break;
                   case LOG.SEVERE:  c = 'S'; break;
                }
                log.setCodigo(String.format("%c%05d", c, l.getIdMsg()));
                lista.add(log);
            }
        }
        return lista;
    }

    private String montaMensaje(LOGLogging l, String lang) {
        Object[] parms = new Object[10];
        for (int idx = 0; idx < 10; idx++) parms[idx] = l.getDato(idx);
        String fmt = msgService.getMessage(l.getIdMsg(), lang);
        return String.format(fmt, parms);
    }
    
    private boolean aplicaFiltros(Object[] filtros, String idMsg, String object, Long tms, String uid) {
        boolean hasFilter = false;
        if (idMsg != null) {
            filtros[0] = Integer.parseInt(idMsg.substring(1));
            hasFilter = true;
        }
        if (object != null) {
            filtros[1] = object;
            hasFilter = true;
        }
        if (tms != null) {
            Long from = 0L, to = 0L;
            calculaTimestamp(from, to, tms);
            filtros[3] = new Timestamp(from);
            filtros[4] = new Timestamp(to);
            hasFilter = true;
        }

        if (uid != null) {
            filtros[2] = uid;
            hasFilter = true;
        }
        return hasFilter;
    }
    
    private void calculaTimestamp(Long from, Long to, Long tms) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(tms);
        cal2.setTimeInMillis(tms);
        
        String interval = cfg.getString(CFG.LOG_TMS, "H").toUpperCase();
        
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        
        if (interval.compareToIgnoreCase(CFG.LOG_TMS_DAY) == 0) {
            cal1.set(Calendar.HOUR, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);                              
            cal2.add(Calendar.DAY_OF_YEAR, 1);
            cal2.set(Calendar.HOUR, 0);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);            
        }
        if (interval.compareToIgnoreCase(CFG.LOG_TMS_HOUR) == 0) {
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            cal2.add(Calendar.HOUR_OF_DAY, 1);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);            
        }
        if (interval.compareToIgnoreCase(CFG.LOG_TMS_MINUTE) == 0) {
            cal1.set(Calendar.SECOND, 0);
            cal2.add(Calendar.MINUTE, 1);
            cal2.set(Calendar.SECOND, 0);            
        }
        
        from = cal1.getTimeInMillis();
        to   = cal2.getTimeInMillis();
    }
}
