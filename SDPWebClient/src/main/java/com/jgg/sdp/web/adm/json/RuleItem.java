package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;

public class RuleItem {

	private Integer   id;
	private String    code;
	private Integer   idGroup;
	private Integer   idItem;
	private Integer   idRule;
	private Integer   activeGroup;
	private Integer   activeItem;
	private Integer   activeRule;
	private Integer   active;
	private String    clave;
	private Integer   priority;
	private String    propiedad;
	private Integer   severity;
	private boolean   negated;
	private Integer   comparator;
	private Integer   tipo;
	private String    valor;
	private String    uid;
	private Timestamp tms;
	private String    desc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public Integer getIdItem() {
		return idItem;
	}
	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
	public Integer getIdRule() {
		return idRule;
	}
	public void setIdRule(Integer idRule) {
		this.idRule = idRule;
	}
	public Integer getActiveItem() {
		return activeItem;
	}
	public void setActiveItem(Integer activeItem) {
		this.activeItem = activeItem;
	}
	public Integer getActiveRule() {
		return activeRule;
	}
	public void setActiveRule(Integer activeRule) {
		this.activeRule = activeRule;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public boolean isNegated() {
		return negated;
	}
	public void setNegated(boolean negated) {
		this.negated = negated;
	}
	public Integer getComparator() {
		return comparator;
	}
	public void setComparator(Integer comparator) {
		this.comparator = comparator;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getActiveGroup() {
		return activeGroup;
	}
	public void setActiveGroup(Integer activeGroup) {
		this.activeGroup = activeGroup;
	}
	
}
