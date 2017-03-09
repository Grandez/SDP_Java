package com.jgg.sdp.excel.data;

import java.text.DateFormat;
import java.util.*;

public class XLSDataTools implements XLSDataRecord {

	private int pos = -1;
	
	public boolean open(String table, Long key) {
		return false;
	}

	public boolean next() {
		return ++pos == 0;
	}

	public boolean close() {
		return false;
	}

	public Object getValue(String member) {
		if (member.compareToIgnoreCase("DATE") == 0) return currentDate();
		return null;
	}

	public int getLevel() {
		return 0;
	}


	private String currentDate() {
		Date now = new Date();
		return DateFormat.getDateTimeInstance().format(now);
	}
}
