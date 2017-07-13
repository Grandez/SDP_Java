package com.jgg.sdp.web.json.modulo;

public class Verbs {

   private int total;	
   private int data;
   private int io;
   private int ctrl;
   private int flow;
   private int arit;
   private int lang;
   private int cics;
   private int sql;
   
   public void calculateTotal() {
	   total = data + io + ctrl + flow + arit + lang + cics + sql;
   }
   
   public int getTotal() {
	return total;
   }
   public void setTotal(int total) {
	this.total = total;
   }
   public int getData() {
	return data;
   }
   public void setData(int data) {
	this.data = data;
   }
   public int getIo() {
	return io;
   }
   public void setIo(int io) {
	this.io = io;
   }
   public int getCtrl() {
	return ctrl;
   }
   public void setCtrl(int ctrl) {
	this.ctrl = ctrl;
   }
   public int getFlow() {
	return flow;
   }
   public void setFlow(int flow) {
	this.flow = flow;
   }
   public int getArit() {
	return arit;
   }
   public void setArit(int arit) {
	this.arit = arit;
   }
   public int getLang() {
	return lang;
   }
   public void setLang(int lang) {
	this.lang = lang;
   }
   public int getCics() {
	return cics;
   }
   public void setCics(int cics) {
	this.cics = cics;
   }
   public int getSql() {
	return sql;
   }
   public void setSql(int sql) {
	this.sql = sql;
   }

}
