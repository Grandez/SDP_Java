package com.jgg.sdp.web.json.modulo;

import java.util.*;

public class Issues {

	private ArrayList<Integer> issues;
	/*
	 * Estado del modulo
	 *  1 - Aprobado manualmente
	 *  0 - Aprobado automaticamente
	 * -1 - Rechazado 
	 */
	private Integer status = 0;

	public ArrayList<Integer> getIssues() {
		return issues;
	}

	public void setIssues(ArrayList<Integer> issues) {
		this.issues = issues;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
