package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;

public class JSonRuleCond {
	
	private Long idCond;
	private Integer idSeq;
	private Integer lvalueType;
	private String lvalue;
	private Integer rvalueType;
	private String rvalue;
	private Integer operator;
	private String  message;
	private String uid;
	private Timestamp tms;
	
	private boolean negated;
	
	public Long getIdCond() {
		return idCond;
	}
	public void setIdCond(Long idCond) {
		this.idCond = idCond;
	}
	public Integer getIdSeq() {
		return idSeq;
	}
	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}
	public Integer getLvalueType() {
		return lvalueType;
	}
	public void setLvalueType(Integer lvalueType) {
		this.lvalueType = lvalueType;
	}
	public String getLvalue() {
		return lvalue;
	}
	public void setLvalue(String lvalue) {
		this.lvalue = lvalue;
	}
	public Integer getRvalueType() {
		return rvalueType;
	}
	public void setRvalueType(Integer rvalueType) {
		this.rvalueType = rvalueType;
	}
	public String getRvalue() {
		return rvalue;
	}
	public void setRvalue(String rvalue) {
		this.rvalue = rvalue;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Timestamp getTms() {
		return tms;
	}
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
	public boolean isNegated() {
		return negated;
	}
	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	
}
