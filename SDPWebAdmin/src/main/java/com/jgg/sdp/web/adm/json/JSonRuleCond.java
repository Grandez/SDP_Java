package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;
import java.util.List;

import com.jgg.sdp.domain.rules.RULCond;

public class JSonRuleCond implements IJSonText {
	
	private Long      idCond;
	private Integer   idSeq;
	private Long      idMsg;
	private Integer   lvalueType;
	private String    lvalue;
	private Integer   rvalueType;
	private String    rvalue;
	private Integer   operator;
	private String    message;
	private String    uid;
	private Timestamp tms;
	
	private boolean negated = false;
	
	/**
	 * Crea la copia del objeto de base de datos sobre JSON
	 * Elimina nulos
	 * @param cond
	 */
	public JSonRuleCond(RULCond cond) {
		idCond     = (cond.getIdCond() == null) ? 0L : new Long(cond.getIdCond());  
		idSeq      = (cond.getIdSeq() == null) ? 0  : new Integer(cond.getIdSeq());
		idMsg      = (cond.getIdMsg() == null) ? 0L : new Long(cond.getIdMsg());
		lvalueType = (cond.getLvalueType() == null) ? 0 : new Integer(cond.getLvalueType());
		lvalue     = cond.getLvalue();
		rvalueType = (cond.getRvalueType() == null) ? 0 : new Integer(cond.getRvalueType());
		rvalue     = cond.getRvalue();
		uid        = cond.getUid();
		tms        = new Timestamp(cond.getTms().getTime());
		if (idCond < 0) {
			negated = true;
			idCond *= -1;
		} 
		
	}
			
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
	public Long getIdMsg() {
		return idMsg == null ? 0 : idMsg;
	}
	
	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
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
	public String getValue() {
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

	public Long getIdDesc() {
		return null;
	}

	public void setDescription(String desc) {
	}

	public List<JSonRuleCond> getActivations() {
		return null;
	}

	
}
