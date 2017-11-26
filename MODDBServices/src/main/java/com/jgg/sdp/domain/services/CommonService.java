/**
 * Simplemente un wrapper de AbstractService
 * 
 */
package com.jgg.sdp.domain.services;

import org.springframework.stereotype.Repository;

@Repository
public class CommonService extends AbstractService<Object> {
	public void executeSQL(String sqlStmt) {
		sqlExecute(sqlStmt);
	}
	public void executeJPA(String sqlStmt) {
		sqlExecute(sqlStmt);
	}

}
