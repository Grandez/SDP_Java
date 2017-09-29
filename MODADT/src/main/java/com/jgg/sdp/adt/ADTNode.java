package com.jgg.sdp.adt;

public class ADTNode<T> {
    public T item;
    public ADTNode<T> next = null;
    
    ADTNode(T item) {
    	this.item = item;
    }

}
