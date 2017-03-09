package com.jgg.sdp.excel.data;

import com.jgg.sdp.excel.map.*;
import com.jgg.sdp.excel.xls.XLSDictionary;

public class XLSData {

	private XLSDictionary dictionary = XLSDictionary.getInstance();
		
	private XLSDataTree   tree  = null;
	private XLSDataTable  table = null;
	private XLSDataQuery  query = null;	
	private XLSDataRecord data = null;
	
	public boolean open(XLSMap map, Long key) {
		String mapName = map.getName().toUpperCase();
		
		if (mapName.compareTo("CALLED") == 0) {
			openTree(0, key, map.getParameterAsInteger(0));
		}
		else if (mapName.compareTo("TREE") == 0) {
			openTree(1, key, map.getParameterAsInteger(0));
		}
		else if (mapName.compareTo("CALLER") == 0) {
			openQuery(map, key);
		}
		else if (mapName.compareTo("MODULE") == 0) {
			openQuery(map, key);
		}
		else if (mapName.compareTo("TOOLS") == 0) {
			openTools(map, key);
		}
		else if (mapName.compareTo("ISSUE") == 0) {
			openIssues(map, key);
		}
		else {
			openTable(map, key);
		}
		
		String name = dictionary.get(map.getName());
		return data.open(name, key);
	}

	private void openTree(int tipo, Long key, int level) {
		tree = new XLSDataTree(tipo, level);
		data = (XLSDataRecord) tree;	
	}
	
	private void openTable(XLSMap map, Long key) {
		table = new XLSDataTable();
		table.close();
		data = (XLSDataRecord) table;		
	}
	
	private void openQuery(XLSMap map, Long key) {
		query = new XLSDataQuery(map.getName().toUpperCase(), key);
		query.close();
		data = (XLSDataRecord) query;				
	}
	
	private void openTools(XLSMap map, Long key) {
		data = (XLSDataRecord) new XLSDataTools();
	}
	
	private void openIssues(XLSMap map, Long key) {
		data = (XLSDataRecord) new XLSDataIssue();
	}
	
	public boolean next() {
		return data.next();
	}

	public Object getValue(XLSField field) {
		String member = translateField(field);
		return data.getValue(member);
	}

	public int getLevel() {
		return data.getLevel();
	}
	
	public boolean close() {
        return data.close();
	}

	private String translateField(XLSField field) {
		String value = dictionary.get(field.getQualifiedName());
		if (value == null) value = field.getFieldName();
 		field.setTableField(value); 
		return field.getTableField();
	}

/*	
	public  final static int DATA_RECORD = 1;
	public  final static int DATA_LIST   = 2;
	
	private final int TABLE   = 0;
	private final int TREE    = 1;
	private final int ROUTINE = 2;
	
	private JDBCService   jdbc       = new JDBCService();
	private XLSDictionary dictionary = XLSDictionary.getInstance();
    private XLSTree       tree       = null;
	
    private XLSDataRecord record = null;
    
	private int type = 0;
	
	public void prepareData(XLSMap map, Long idVersion) {
		
		if (map.getName().compareToIgnoreCase("TREE") == 0) {
			type = TREE;
		}
		else if (map.getName().compareToIgnoreCase("ROUTINE") == 0) {
			 loadRoutines(idVersion);
		}
		else {
		   type = TABLE;
		   jdbc.executeQuery("SELECT * FROM " + map.getTable() + " WHERE idVersion = " + idVersion);
		}
		record = (XLSDataRecord) new 
	}
	
	public void close() {
         if (type == TABLE) jdbc.closeQuery();
	}
	
	public boolean getNext(boolean full) {
		switch (type) {
		   case TABLE:   return jdbc.next();
		   case ROUTINE: return tree.next(full);
		}
		return false;
	}
	
	public Object getValue(XlsField field) {
		String member = translateField(field);
		switch (type) {
		   case TABLE: return getValueFromTable(member);
		   case ROUTINE: return getV
		}
		return null;
	}
	
	private String translateField(XlsField field) {
		String value = dictionary.get(field.getQualifiedName());
		return (value == null) ? field.getFieldName() : value;
	}

	private Object getValueFromTable(String member) {
		int column = jdbc.getColumn(member);
		if (column == 0) return null;
		DBField f = jdbc.getValue(column); 
		return (f == null) ? null : f.getValue();
	}

    private void loadRoutines(Long idVersion) {
		type = ROUTINE;
		if (tree == null) tree = new XLSTree(idVersion); 
	}
*/	
}