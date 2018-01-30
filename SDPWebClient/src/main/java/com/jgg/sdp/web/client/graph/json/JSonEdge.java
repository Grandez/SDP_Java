package com.jgg.sdp.web.client.graph.json;

public class JSonEdge implements Comparable<JSonEdge> {
	
	public static final int NORMAL    = 0;
	public static final int LOOP      = 1;
	public static final int RECURSIVE = 12;
	public static final int REC_END   = 13;
	
	private Long  from;
	private Long  to;
	private int   level = Integer.MAX_VALUE;
	private int   type  = 0;
	
	private Object data;
	
	public JSonEdge() {
		
	}
	public JSonEdge(Long from, Long to) {
		this.from  = from;
		this.to    = to;
	}

	public JSonEdge(Long from, Long to, int level) {
		this(from ,to);
		this.level = level;
	}

	public JSonEdge(Long from, Long to, Integer type) {
		this(from ,to);
		this.type = type;
	}
	
	
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int compareTo(JSonEdge n) {
		return level - n.level;
    }
	
	@Override
	public String toString() {
		return from + " -> " + to; 
	}
	
}
