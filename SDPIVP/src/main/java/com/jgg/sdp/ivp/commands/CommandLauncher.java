package com.jgg.sdp.ivp.commands;

import javax.xml.bind.JAXBElement;

import com.jgg.sdp.domain.services.CommonService;
import com.jgg.sdp.ivp.jaxb.*;
import com.jgg.sdp.tools.Reflect;

public class CommandLauncher {

	private CommonService commonService = new CommonService();
	
	public void process(IVPCommands commands) {
		if (commands == null) return;
        
		commonService.beginTrans();
		for (JAXBElement<?> command : commands.getCommentOrModuleOrComponent()) {
		    System.out.println(command.getName());
			if (command.getName().toString().compareToIgnoreCase("MODULE") == 0) {
				processModule((IVPModule) command.getValue());
			}
			if (command.getName().toString().compareToIgnoreCase("COMPONENT") == 0) {
				processComponent((IVPComponent) command.getValue());
			}
		}
		
		commonService.commitTrans();
	}
	
	private void processModule(IVPModule module) {
		String className = Reflect.findModule(module.getName());
		if (className == null) return;
		
		String[] parms = mountParameters(module.getParameters(), true);
		Reflect.executeJavaMain(className, parms);
	}
	
	private void processComponent(IVPComponent component) {
		IVPObjectName objName = component.getObject();
		String[] parms = mountParameters(component.getParameters(), false);
		Reflect.executeInstance(objName.value() + "Manager", component.getMethod(), parms);
	}
	
	private String[] mountParameters(IVPParameters parameters, boolean ivp) {
		int idx = 0;
		if (parameters == null) return new String[0];
		int size = parameters.getParameter().size();
		if (ivp) size += 1;
		String[] parms = new String[size];
		if (ivp) {
			parms[idx] = "--IVP";
			idx++;
		}
		for (String p : parameters.getParameter()) {
			parms[idx++] = p;
		}
		
		return parms;
	}
}
