package com.jgg.sdp.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SDPTree<Item> implements Iterable<Item> {
    private ADTNode<Item> root;     // beginning of list
    private ADTNode<Item> last;     // end of list
    private int  depth;
    private int  size;
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
