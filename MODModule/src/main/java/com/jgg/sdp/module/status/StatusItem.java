package com.jgg.sdp.module.status;

public class StatusItem implements Comparable<StatusItem> {

   private int idGroup;	
   private int idItem;
   private int actual;
   private int maximo;
   private int excepcion;
   private int progreso;
   private int delta;
   private int status;
   
   public StatusItem(int idGroup, int idItem) {
	   this.idGroup = idGroup;
	   this.idItem = idItem;
   }

public int getIdGroup() {
	return idGroup;
}

public void setIdGroup(int idGroup) {
	this.idGroup = idGroup;
}

public int getIdItem() {
	return idItem;
}

public void setIdItem(int idItem) {
	this.idItem = idItem;
}

public int getActual() {
	return actual;
}

public void setActual(int actual) {
	this.actual = actual;
}

public int getMaximo() {
	return maximo;
}

public void setMaximo(int maximo) {
	this.maximo = maximo;
}

public int getExcepcion() {
	return excepcion;
}

public void setExcepcion(int excepcion) {
	this.excepcion = excepcion;
}

public int getProgreso() {
	return progreso;
}

public void setProgreso(int progreso) {
	this.progreso = progreso;
}

public int getDelta() {
	return delta;
}

public void setDelta(int delta) {
	this.delta = delta;
}

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + actual;
	result = prime * result + delta;
	result = prime * result + excepcion;
	result = prime * result + idGroup;
	result = prime * result + idItem;
	result = prime * result + maximo;
	result = prime * result + progreso;
	result = prime * result + status;
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	StatusItem other = (StatusItem) obj;
	if (actual != other.actual)
		return false;
	if (delta != other.delta)
		return false;
	if (excepcion != other.excepcion)
		return false;
	if (idGroup != other.idGroup)
		return false;
	if (idItem != other.idItem)
		return false;
	if (maximo != other.maximo)
		return false;
	if (progreso != other.progreso)
		return false;
	if (status != other.status)
		return false;
	return true;
}
   
   public int compareTo(StatusItem n) {
       int lastCmp = idGroup - n.idGroup;
       if (lastCmp != 0) return lastCmp;
       return idItem - n.idItem;
   }

}
