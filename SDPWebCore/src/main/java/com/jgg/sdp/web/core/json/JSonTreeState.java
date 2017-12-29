package com.jgg.sdp.web.core.json;

public class JSonTreeState {

	private boolean opened   = true;
	private boolean disabled = false;
	private boolean select   = false;
	
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	
}
