/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.session.*;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.web.rest.services.*;
import com.jgg.sdp.core.tools.Fechas;

@RestController
public class EjecucionesController {

    @Autowired
    private SDPModuloService modService;
    
    @Autowired
    private SESModuloService sesService;

    @Autowired
    private AplicacionService appService;
    
    /**
     * Gets the ejecuciones.
     *
     * @param idAppl
     *            the id appl
     * @param rango
     *            the rango
     * @return the ejecuciones
     */
    @RequestMapping("/exe/{idAppl}/{rango}")
    public List<Ejecucion> getEjecuciones(@PathVariable Long idAppl, @PathVariable Integer rango) {
        List<Ejecucion> datos   = new ArrayList<Ejecucion>();
        List<String>    modulos = new ArrayList<String>(); 

       Timestamp inicio = Fechas.calculaInicio(rango);       
        for (SDPModulo mod: modService.getModulesByAppl(idAppl)) {
           modulos.add(mod.getNombre());   
        }

        if (modulos.size() > 0) {
           for (Object[] tupla: sesService.getResumenEjecuciones(modulos, inicio)) {
               Ejecucion eje = new Ejecucion();
               eje.setModulo ((String) tupla[0]);
               eje.setVeces  ((Long) tupla[1]);
               eje.setElapsed((Long) tupla[2]);
               datos.add(eje);
           }
        }   
        return datos;
    }

}
