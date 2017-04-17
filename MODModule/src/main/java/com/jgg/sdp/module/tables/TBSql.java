package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.*;

public class TBSql {

	private ArrayList<SQLItem> sqlItems = new ArrayList<SQLItem>();
	private ArrayList<SQLCode> sqlCodes = new ArrayList<SQLCode>();
	
	public void addSql(SQLItem item, SQLCode code) {
		sqlItems.add(item);
		sqlCodes.add(code);
	}

	public List<SQLItem> getSqlItems() {
		return sqlItems;
	}

	public List<SQLCode> getSqlCodes() {
		return sqlCodes;
	}

}
