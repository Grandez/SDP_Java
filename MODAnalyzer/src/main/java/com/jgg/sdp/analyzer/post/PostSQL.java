package com.jgg.sdp.analyzer.post;

import com.jgg.sdp.core.tools.*;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.parser.base.ParserInfo;
import com.jgg.sdp.parser.stmt.StmtSQL;
import com.jgg.sdp.tools.Zipper;

public class PostSQL {

	private StmtSQL stmt   = null;
	private Module  module = null;
	private Source  source = null;
	
	private ParserInfo info = ParserInfo.getInstance();
	
	public PostSQL(Module module) {
		this.module = module;
	}

	public void setStatement(StmtSQL sql) {
		this.stmt = sql;
	}
	
	public void setSource(Source src) {
		source = src;
	}
	public void parse() {
//		System.out.println("SQL: " + stmt.getVerbName());
		prepareVariables();
		
		String hash = Firma.calculate(new String(source.getData()).getBytes());

		/*
		 * Cada sentencia SQL se procesa en su parser
		 * Asi que en la pila de offsets hay al menos 2: Modulo principal + Inicio parser
		 * Si hay COPYS/INCLUDES anidadas la profundidad sube, pero la primera siempre
		 * es el segundo nivel
		 */
		
		stmt.setBegLine(info.getOffset() + stmt.getBegLine());
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
		SQLCode sqlCode = new SQLCode();
		sqlCode.setBegLine(stmt.getBegLine());
		sqlCode.setFirma(hash);
		sqlCode.setStmt(Zipper.zip(stmt.getVerbName(), source.getData()));
		return sqlCode;
	}
	
	private SQLItem storeStatementInfo(String hash) {
		SQLItem sqlItem = new SQLItem();
		sqlItem.setBegLine(stmt.getBegLine());
		sqlItem.setFirma(hash);
		sqlItem.setComplexity(0);
		sqlItem.setExplanation(0);
		sqlItem.setVerb(stmt.getVerbName());
		return sqlItem;
	}
}
