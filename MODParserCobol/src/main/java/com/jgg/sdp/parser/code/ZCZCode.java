package com.jgg.sdp.parser.code;

import com.jgg.sdp.common.config.*;

import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.parser.base.ParserInfo;

public class ZCZCode {

	protected Configuration cfg    = DBConfiguration.getInstance();	
	protected Module        module = null;
	protected ParserInfo    info   = null;
	
    public ZCZCode(Module module) {
	   	this.module = module;
        this.info = ParserInfo.getInstance();
	}
    
}
