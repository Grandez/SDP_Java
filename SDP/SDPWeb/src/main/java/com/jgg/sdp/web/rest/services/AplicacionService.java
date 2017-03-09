package com.jgg.sdp.web.rest.services;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Fechas;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.*;
import com.jgg.sdp.web.pojo.ModuloPojo;

@Service
public class AplicacionService {

    @Autowired
    private SDPAplicacionService appService;

    @Autowired
    private SDPModuloService modService;

    @Autowired
    private MODVersionService versionService;
    
    @Autowired
    private StatsService stats;

    private Configuration cfg = DBConfiguration.getInstance();

    
    public Set<ModuloPojo> getConjuntoModulos(Long idAppl) {
        Set<ModuloPojo> datos = new HashSet<ModuloPojo>();
        for (SDPModulo mod: modService.getModulesByAppl(idAppl)) {
            ModuloPojo pojo = new ModuloPojo();
            pojo.setIdModulo(mod.getIdModulo());
            pojo.setNombre(mod.getNombre());
            MODVersion ver = versionService.getCurrentVersion(mod.getIdModulo());
            if (ver != null) {
                pojo.setIdVersion(ver.getIdVersion());
                datos.add(pojo);
            }
        }
        return datos;
    }
    public Area getArea(Long id, Integer rango) {
        Area area = new Area();
        
        Set<Long>     grupo  = appService.getConjuntoActivo(id);
        Timestamp     inicio = Fechas.calculaInicio(rango);
        SDPAplicacion app    = appService.findById(id);

        if (app == null && id != 0) return area;
        
        area.setExist(true);
        area.setId(id);

        if (id == 0) {
            area.setNombre(cfg.getString(CFG.INST_NAME));
        }
        else {
           area.setNombre(app.getAplicacion());
        }   
        area.hijos = cargarAplicaciones(id);

        area.stats   = stats.cargarEstadisticas(area, grupo, inicio);

        return area;
    }

    private ArrayList<Appl> cargarAplicaciones(Long id) {
        ArrayList<Appl> lista = new ArrayList<Appl>();
        List<SDPAplicacion> appList = appService.listByPadreId(id);
        for (int index = 0; index < appList.size(); index++) {
            SDPAplicacion a = appList.get(index);
            Appl appl = new Appl();
            appl.setId(a.getId());
            appl.setNombre(a.getAplicacion());
            appl.setAplicaciones(0);
            appl.setModulos(a.getVolumen());
            appl.setOrden(index + 1);
            cargarSubAplicaciones(appl, a.getId());
            lista.add(appl);
        }
        return lista;
    }

    private void cargarSubAplicaciones(Appl appl, Long id) {
        List<SDPAplicacion> appList = appService.listByPadreId(id);
        appl.setAplicaciones(appl.getAplicaciones() + appList.size());
        for (SDPAplicacion app : appList) {
            appl.setModulos(appl.getModulos() + app.getVolumen());
            cargarSubAplicaciones(appl, app.getId());
        }        
    }
    

}