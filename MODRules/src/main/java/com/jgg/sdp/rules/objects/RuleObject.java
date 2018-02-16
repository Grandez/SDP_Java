package com.jgg.sdp.rules.objects;

import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.blocks.reflect.Reflect;

public class RuleObject {
	private int begLine   = 0;
	private int begColumn = 0;
	private int endLine   = 0;
	private int endColumn = 0;

	private Object value  = 0;
	private String bloque = "";
	private Object root   = null;
	
	private List<Object> lstComponents = new ArrayList<Object>();
	private int          idxComponents;
	private List<Object> lstParents    = null;
	private int          idxParents;	
	private Object       component     = null;
	
    private Object  lval;
    private Object  rval;
    private Integer oper;
    
    public RuleObject() {	
    }
    
    public RuleObject(Object o) {
    	setComponent(o);
    	Reflect i = (Reflect) o;
    	begLine    = i.getBegLine();
    	begColumn  = i.getBegColumn();
    	endLine    = i.getEndLine();
    	endColumn  = i.getEndColumn();
    }
    
    public boolean getFirstComponent() {
    	idxComponents = 0;
    	return setComponent(idxComponents);
    }
    public boolean getNextComponent() {
    	if (lstComponents.size() <= (idxComponents + 1)) 
    		return false;
    	return setComponent(++idxComponents);
    }
    private boolean setComponent(int index) {
    	if (lstComponents.size() <= index) return false;
    	component = lstComponents.get(index);
    	return true;
    }
        
	public int getBegLine() {
		return begLine;
	}


	public void setBegLine(int begLine) {
		this.begLine = begLine;
	}


	public int getBegColumn() {
		return begColumn;
	}


	public void setBegColumn(int begColumn) {
		this.begColumn = begColumn;
	}


	public int getEndLine() {
		return endLine;
	}


	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public Object getComponent() {
		return component;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	public Object getRoot() {
		return root;
	}

	@SuppressWarnings("unchecked")
	public void setComponent(Object component) {
		if (component instanceof List) {
			lstComponents = (List<Object>) component;
		}
		else {
			if (lstComponents.size() == 0) {
				lstComponents.add(component);
			} else {
			    lstComponents.set(0, component);
			}
			this.component = component;
		}
	}
	
    public void setValue(Object value) {
    	this.value = value;
    }

    public Object getValue() {
    	return value;
    }
    
	public String getObjectAsString() {
		if (component == null) return null;
		return component.toString();
	}

	public String getObjectValue() {
		if (component == null) return null;
		return ((Reflect) component).getValue();
	}
	
	public Object getLVal() {
		return lval;
	}

	public void setLVal(Object lval) {
		this.lval = lval;
	}

	public Object getRVal() {
		return rval;
	}

	public void setRVal(Object rval) {
		this.rval = rval;
	}

	public Integer getOperator() {
		return oper;
	}

	public void setOperator(Integer oper) {
		this.oper = oper;
	}
	
	// Metodos dinamicos
	
	public int getLength() {
		if (component == null) return 0;
		return ((String) component).length();
	}
	
	public void saveComponent() {
		lstParents = copyArray(lstComponents);
		idxParents = idxComponents;
	}
	public void restoreComponent() {
		lstComponents = copyArray(lstParents);
		idxComponents = idxParents;
		component = lstComponents.get(idxComponents);
	}
	private List<Object> copyArray(List<Object> from) {
		List<Object> to = new ArrayList<Object>();
		while (from.size() > 0) to.add(from.remove(0));
		return to;
	}	
}
