package com.jgg.sdp.excel.data;

import java.util.ArrayList;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;

public class XLSDataQuery implements XLSDataRecord {

	private Long   key  = null;

	private JDBCService   jdbc       = new JDBCService();
	
	private SDPModuloService  moduloService  = new SDPModuloService();
	private MODVersionService versionService = new MODVersionService();
	private MODCallService    callService    = new MODCallService();	
	
	private ArrayList<String> datos = new ArrayList<String>();
	
    private int pos = -1;
    private int tipo = 0;
	
	public XLSDataQuery(String name, Long key) {
	    this.key = key;
	}
	
	public boolean open(String table, Long key) {
		if (table.compareTo("MOD_VERSIONES") == 0) {
			tipo = 0;
			return openModule(key);
		}
		tipo = 1;
		return openParents();
	}

	public boolean next() {
		if (tipo == 0) return jdbc.next();

		pos++;
        if (pos >= datos.size()) return false;
		return true;
	}

	public boolean close() {
		if (tipo == 0) jdbc.closeQuery();
		return false;
	}

	public Object getValue(String member) {
		if (tipo == 0) return jdbc.getValue(member);
		return datos.get(pos);
	}

	public int getLevel() {
		return 1;
	}

	private boolean openModule(Long idVersion) {
		String sqlStmt = "SELECT A.*, B.versiones FROM sdp.mod_versiones AS A " +
	                     " INNER JOIN sdp.sdp_modulos AS B ON A.idModulo = B.idModulo " +
				         " WHERE A.idVersion = ";
		
		jdbc.executeQuery(sqlStmt + idVersion);
		pos = -1;
		return false;
	}
	
	private boolean openParents() {
		SDPModulo modulo = null;
		String modName = getModuleName();
		if (modName == null) return true;
		for (MODCall calling : callService.getListCalling(modName)) {
			modulo = moduloService.findByVersion(calling.getIdVersion());
			if (modulo != null) datos.add(modulo.getNombre());
		}
		pos = -1;
		return false;
	}
		
	private String getModuleName() {
		MODVersion version = versionService.getByVersion(key);
		if (version == null) return null;
		return version.getNombre();
	}	
		
}
