package com.jgg.sdp.web.adm.json;

import java.sql.Timestamp;

public class RuleItem {

	private Long      id;
	private String    code;
	private Long      idGroup;
	private Long      idItem;
	private Long      idRule;
	private Long      activeGroup;
	private Long      activeItem;
	private Long      activeRule;
	private Long      active;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}
	public Long getIdItem() {
		return idItem;
	}
	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}
	public Long getIdRule() {
		return idRule;
	}
	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}
	public Long getActiveItem() {
		return activeItem;
	}
	public void setActiveItem(Long activeItem) {
		this.activeItem = activeItem;
	}
	public Long getActiveRule() {
		return activeRule;
	}
	public void setActiveRule(Long activeRule) {
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
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	public Long getActiveGroup() {
		return activeGroup;
	}
	public void setActiveGroup(Long activeGroup) {
		this.activeGroup = activeGroup;
	}
	
}
