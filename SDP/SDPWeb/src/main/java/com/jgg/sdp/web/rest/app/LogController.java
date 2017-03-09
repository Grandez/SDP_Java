package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.ctes.LOG;
import com.jgg.sdp.core.tools.Fechas;
import com.jgg.sdp.domain.log.*;
import com.jgg.sdp.domain.services.core.SDPAplicacionService;
import com.jgg.sdp.domain.services.log.*;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.core.LANG;
import com.jgg.sdp.web.json.*;

@RestController
public class LogController {

    @Autowired
    private SDPAplicacionService appService;
    
    @Autowired
    LOGOutputService logService;

    @Autowired
    LOGMsgService msgService;
    
    private Configuration cfg = DBConfiguration.getInstance();

    @RequestMapping("/log/{id}")
    public List<Log> getLog(@PathVariable Long id,  @RequestHeader HttpHeaders headers) {
        return getLog(id, cfg.getInteger(CFG.DATE_INTERVAL), headers);
    }
    
    @RequestMapping("/log/{id}/{rango}")
    public List<Log> getLog(@PathVariable Long id, @PathVariable Integer rango,  @RequestHeader HttpHeaders headers) {
        List<Log> lista = new ArrayList<Log>();
        Set<Long> grupo = appService.getConjuntoActivo(id);
        String lang = LANG.getLanguage(headers);
        Timestamp from = calculaInicio(rango);

        for (LOGLogging l : logService.list(from, 100)) {
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
    
    private Timestamp calculaInicio (Integer rango) {
        switch (rango) {
           case CDG.RANGE_DAY:     return Fechas.subDays(1);
           case CDG.RANGE_WEEK:    return Fechas.subDays(7);
           case CDG.RANGE_MONTH:   return Fechas.subMonths(1);
           case CDG.RANGE_QUARTER: return Fechas.subMonths(3);
        }
        return new Timestamp(0L);
    }

}
