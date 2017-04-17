package com.jgg.sdp.domain.services.core;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPDependencia;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPDependenciaService extends AbstractService<SDPDependencia> {

	private String module = "";
	private HashMap<String, SDPDependencia> cache = null;
	
	public List<SDPDependencia> getDependencias(String module, String variable) {
		return lista(SDPDependencia.listByVariable, module, variable);
	}
	
	public void deleteModule(String[] data) {
		deleteQuery(SDPDependencia.deleteByModule, data[0]);
	}

	public void deleteVariable(String[] data) {
		deleteQuery(SDPDependencia.deleteByVariable, data[0], data[1]);
	}
	
	public void addDependence(String[] data) {
	   if (module.compareTo(data[0]) != 0) loadCache(data[0]);
	   String key = data[1] + data[2];
	   if (cache.get(key) == null) {
		   addDepToCache(data);
	   }
	}
	
	public void deleteDependence(String[] data) {
	   if (module.compareTo(data[0]) == 0) {
		   SDPDependencia d = cache.get(data[1] + data[2]);
		   if (d != null) {
			   remove(d);
			   cache.remove(data[1] + data[2]);
			   return;
		   }
	   }
	   deleteQuery(SDPDependencia.deleteByCalled, data[0], data[1], data[2]);
	}
	
	private void loadCache(String module) {
		this.module = module;
		cache = new HashMap<String, SDPDependencia>();
		for (SDPDependencia d: lista(SDPDependencia.listByModule, module)) {
			cache.put(d.getVariable() + d.getCalled(), d);
		}
	}
	
	private void addDepToCache(String[] data) {
		SDPDependencia dep = new SDPDependencia();
		dep.setModulo(data[0]);
		dep.setVariable(data[1]);
		dep.setCalled(data[2]);
		dep.setUid(System.getProperty("user.name"));
		dep.setTms(new Timestamp(System.currentTimeMillis()));
		update(dep);
		cache.put(data[1] + data[2], dep);
	}
}
