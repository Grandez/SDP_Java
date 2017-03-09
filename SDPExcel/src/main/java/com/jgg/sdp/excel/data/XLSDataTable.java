package com.jgg.sdp.excel.data;

import com.jgg.sdp.domain.services.JDBCService;

public class XLSDataTable implements XLSDataRecord {

	private JDBCService   jdbc       = new JDBCService();
	private boolean       qryOpen   = false;

	public boolean open(String table, Long key) {
		if (table == null) return true;
		jdbc.executeQuery("SELECT * FROM " + table + " WHERE idVersion = " + key);
		qryOpen = true;
		return false;
	}

	public boolean next() {
        return jdbc.next();
    }

	public boolean close() {
		if (qryOpen) jdbc.closeQuery();
		qryOpen = false;
		return false;
	}

	public Object getValue(String member) {
//		int column = jdbc.getColumn(member);
//		if (column == 0) return null;
		return jdbc.getValue(member); 
	}	
	
	// Se utiliza en los casos que hay niveles
	public int getLevel() {
		return 0;
	}
}
