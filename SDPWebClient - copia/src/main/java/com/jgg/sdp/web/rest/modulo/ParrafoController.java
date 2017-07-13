/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.summary.*;
import com.jgg.sdp.domain.summary.SUMParrafo;

@RestController
public class ParrafoController {

    @Autowired
    SDPModuloService moduloService;
    @Autowired 
    SUMParrafoService parrafoService;
    
    /**
     * Gets the parrafos usage.
     *
     * @param idModulo
     *            the id modulo
     * @return the parrafos usage
     */
    @RequestMapping("/paragraph/{idModulo}")
    public List<SUMParrafo> getParrafosUsage(@PathVariable Long idModulo) {
        SDPModulo modulo = moduloService.findById(idModulo);
        return parrafoService.getParrafosByName(modulo.getIdVersion());
    }

}
