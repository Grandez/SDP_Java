/**
 * Servicio REST para devolver la información relativa a los arboles de llamadas
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.web.rest.modulo;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.log.LOGMsgService;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.summary.*;
import com.jgg.sdp.domain.summary.*;
import com.jgg.sdp.web.json.*;

@RestController
public class ArbolController {

    @Autowired
    private SDPModuloService moduloService;
    @Autowired
    private MODVersionService versionService;
    
    @Autowired
    private SUMModuloService sumModuloService;
    
    @Autowired
    private SUMArbolService sumArbolService;

    @Autowired
    LOGMsgService      msgService;
    
    private final SimpleDateFormat fmt = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
    
    /**
     * Devuelve la lista de modulos llamados indicando quien lo llamo
     *
     * @param idVersion Version del modulo principal
     * @return la lista de modulos invocados
     */
    @RequestMapping("/tree/{idVersion}")
    public List<Arbol> getArbolByModule(@PathVariable Long idVersion,
                                        @RequestHeader HttpHeaders headers) {

        int last = 1;
        
        List<Arbol> arbol = new ArrayList<Arbol>();
        Set<Long>   nodos = new HashSet<Long>();
        
        MODVersion ver = versionService.getByVersion(idVersion);
        SUMModulo mod = sumModuloService.find(idVersion);
        
        if (ver != null) {
            Arbol nodo = new Arbol();
            nodo.setId(last);
            nodo.setIdModulo(ver.getIdModulo());
            nodo.setIdVersion(idVersion);
            nodo.setNombre(ver.getNombre());
            nodo.setParent(null);
            nodo.setTms(fmt.format(new Date()));
            nodo.setVeces(mod.getVeces());
            nodo.setAvgElapsed(mod.getAvgElapsed() / 1000);
            if (mod.getAvgElapsed() == 0) {
                nodo.setPrcCpu(0L);
                nodo.setPrcSuspend(0L);                 
            }
            else {
               nodo.setPrcCpu(mod.getAvgCpu() * 100 / mod.getAvgElapsed());
               nodo.setPrcSuspend(100 - nodo.getPrcCpu());
            }   

            arbol.add(nodo);
            cargaArbolSumarizado(arbol, nodos, nodo, last);
        }
        return arbol;        
    }
    
    private int cargaArbolSumarizado(List<Arbol> arbol, Set<Long> nodos, Arbol nodo, int last) {
        int  ultimo = last;

        // Miramos si ya lo hemos procesado
        if (nodos.contains(nodo.getIdVersion())) {
            return last;
        }
        
        nodos.add(nodo.getIdVersion());
        
        for (SUMArbol sum : sumArbolService.getCalled(nodo.getIdVersion())) {
             Arbol a = new Arbol();
             a.setId(++ultimo);
             a.setParent(nodo.getId());
             a.setIdVersion(sum.getIdCalled());
             a.setNombre(sum.getNombre());
             a.setVeces(sum.getVeces());
             a.setTms(fmt.format(sum.getTms()));
             a.setAvgElapsed(sum.getAvgElapsed() / 1000);
             if (sum.getAvgElapsed() == 0) {
                 a.setPrcCpu(0L);
                 a.setPrcSuspend(0L);                 
             }
             else {
                a.setPrcCpu(sum.getAvgCpu() * 100 / sum.getAvgElapsed());
                a.setPrcSuspend(100 - a.getPrcCpu());
             }   
             arbol.add(a);
             ultimo = cargaArbolSumarizado(arbol, nodos, a, ultimo);
        }        

        return last;
    }
    
}
