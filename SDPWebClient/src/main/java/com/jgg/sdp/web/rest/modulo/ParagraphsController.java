/*
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.MODParrafo;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.MODParrafoService;

@RestController
public class ParagraphsController {

    @Autowired
    SDPModuloService moduloService;
    @Autowired 
    MODParrafoService parrafoService;
    
    @RequestMapping("/paragraphs/{idModulo}")
    public List<MODParrafo> getParagraphsInfo(@PathVariable Long idModulo) {
        SDPModulo modulo = moduloService.findById(idModulo);
        return parrafoService.getParrafos(modulo.getIdVersion());
    }

}
