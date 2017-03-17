package com.jgg.sdp.parser.post;

import com.jgg.sdp.core.tools.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.parser.db2.stmt.StmtSQL;

public class PostSQL {

	private StmtSQL stmt   = null;
	private Module  module = null;
	private Source  source = null;
	
	public PostSQL(Module module, Object stmt) {
		this.module = module;
		this.stmt = (StmtSQL) stmt;
	}

	public void setSource(Source src) {
		source = src;
	}
	public void parse() {
		prepareVariables();
		
		String hash = Firma.calculate(new String(source.getData()).getBytes());
		
		calculateComplexity();
		SQLCode sqlc = storeStatementCode(hash);
		SQLItem sqli = storeStatementInfo(hash);
		module.addSql(sqli, sqlc);
	}

	private void prepareVariables() {
		
	}
	
	private void calculateComplexity() {
		
	}
	
	private SQLCode storeStatementCode(String hash) {
		SQLCode sqlStmt = new SQLCode();
		sqlStmt.setBegLine(stmt.getBegLine());
		sqlStmt.setFirma(hash);
		sqlStmt.setStmt(Zipper.zip(stmt.getVerbName(), source.getData()));
		return sqlStmt;
	}
	
	private SQLItem storeStatementInfo(String hash) {
		SQLItem sqlStmt = new SQLItem();
		sqlStmt.setBegLine(stmt.getBegLine());
		sqlStmt.setFirma(hash);
		sqlStmt.setComplexity(0);
		sqlStmt.setExplanation(0);
		sqlStmt.setVerb(stmt.getVerbName());
		return sqlStmt;
	}
}
