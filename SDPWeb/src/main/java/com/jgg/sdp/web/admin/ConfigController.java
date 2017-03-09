/*
 * 
 */
package com.jgg.sdp.web.admin;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.base.*;
import com.jgg.sdp.domain.services.base.*;
import com.jgg.sdp.domain.services.log.LOGInputService;
import com.jgg.sdp.web.core.LANG;
import com.jgg.sdp.core.ctes.LOG;
import com.jgg.sdp.core.ctes.SYS;

@RestController
public class ConfigController {

    @Autowired
    private CFGConfiguracionService cfgService;

    @Autowired
    private CFGCodigoService cdgService;
    @Autowired
    LOGInputService logService;
    

    
    @RequestMapping("/admin/config")
    public List<CFGConfiguracion> getConfig() {
        return cfgService.getAll();
    }

    /**
     * Sets the config.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    @RequestMapping(value="/admin/config/set", method=RequestMethod.POST)
    public void setConfig(@RequestParam(value = "key",   required = true) String key,
                          @RequestParam(value = "value", required = true) String value) {
        String oldValue = null;
        cfgService.beginTrans();
        CFGConfiguracion cfg = cfgService.get(key);
        if (cfg == null) return;
                
        oldValue = cfg.getValor();
        cfg.setValor(value);
        logService.logWeb(LOG.CFG_CHANGED, SYS.MOD_CONFIG, "Config", "Admin", oldValue, cfg.getValor());
        cfgService.commitTrans();
    }
    
    /**
     * Gets the config labels.
     *
     * @param headers
     *            the headers
     * @return the config labels
     */
    @RequestMapping("/admin/config/label")
    public List<String> getConfigLabels(@RequestHeader HttpHeaders headers) {
        String lang = LANG.getLanguage(headers);
        List<CFGCodigo> aux = cdgService.getCodigos(SYS.CDG_CONFIG, lang);
        List<String> lista = new ArrayList<String>();
        for (CFGCodigo cdg : aux) {
           lista.add(cdg.getValor()); 
        }
        return lista;
    }

    /**
     * Descarga.
     *
     * @param headers
     *            the headers
     * @param response
     *            the response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @RequestMapping("/admin/config/get")
    public void descarga(@RequestHeader HttpHeaders headers, 
                         HttpServletResponse response ) throws IOException {
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition","attachment;filename=SDP.Properties");
            ServletOutputStream out = response.getOutputStream();
            List<CFGConfiguracion> lista = cfgService.getAll();
            for (CFGConfiguracion cfg : lista) {
                out.println(cfg.getClave() + " = " + cfg.getValor());    
            }
            
            out.flush();
            out.close();
        }
}
