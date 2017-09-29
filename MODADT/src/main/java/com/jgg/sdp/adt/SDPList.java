package com.jgg.sdp.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SDPList<Item> implements Iterable<Item> {
    private Node first;    // beginning of list
    private Node last;     // end of list
    private int n;         // number of elements in bag

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        Node(Item item) {
        	this.item = item;
        	this.next = null;
        }
    }

    public SDPList() {
        first = null;
        n = 0;
    }
    public boolean isEmpty() { return first == null;  }
    public int size()        { return n;              }

    public void add(Item item) { addLast(item); }
    public void remove(Item item) {
    	Node tmp = first;
    	Node prev = null;
    	while (tmp != null) {
    		if (tmp.item.equals(item)) {
    			if (prev != null) {
    				prev.next = tmp.next;
    			} else {
    				first = tmp.next;
    			}
    		}
    		else {
    			prev = tmp;
    		}
   			tmp = tmp.next;
       }
    	last = (prev == null) ? first : prev;
    }
    
    public void addFirst(Item item) {
    	if (first == null) {
    	   addFirstItem(item);
    	} else {
    	   Node oldFirst = first;
    	   first = new Node(item);
    	   first.next = oldFirst;
    	}
    	n++;
    }

    public void addLast(Item item) {
    	if (first == null) {
    		addFirstItem(item);
    	} else {
    	  last.next = new Node(item);
    	  last = last.next;
    	}
        n++;
    }

    private void addFirstItem(Item item) {
        first = new Node(item);
        last = first;
    }
    
    /**
     * No se pueden crear objectos genericos
     * Es responabilidad del receptor haer el cast
     * @return
     */
	public Object[] asObjectArray() {
    	int idx = 0;
    	Object[] o = new Object[n];
    	Node n = first;
    	while (n != null) {
    		o[idx++] = n.item;
    		n = n.next;
    	}
    	return o; 
    }
    
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
