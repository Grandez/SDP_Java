/**
 * Simplemente un wrapper de AbstractService
 * 
 */
package com.jgg.sdp.domain.services;

import org.springframework.stereotype.Repository;

@Repository
public class CommonService extends AbstractService<Object> {
	public void execute(String sqlStmt) {
		sqlExecute(sqlStmt);
	}

}
