package com.jgg.sdp.web.admin.rest.core;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.cfg.CFGConfiguracion;
import com.jgg.sdp.web.core.*;


@RestController
public class ConfigController {

	
    @RequestMapping(value="admin/configuration", method=RequestMethod.GET)
    public List<CFGConfiguracion> getConfiguration1() {
        return DBConfiguration.getRawConfiguration();
    }
 
    @RequestMapping(value="/configuration", method=RequestMethod.GET)
    public List<CFGConfiguracion> getConfiguration2() {
        return DBConfiguration.getRawConfiguration();
    }

    @RequestMapping(value="SDPWebAdmin/configuration", method=RequestMethod.GET)
    public List<CFGConfiguracion> getConfiguration3() {
        return DBConfiguration.getRawConfiguration();
    }
    
}
