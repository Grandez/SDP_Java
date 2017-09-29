package com.jgg.sdp.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SDPBag<Item> implements Iterable<Item> {
    private ADTNode<Item> first;    // beginning of bag
    private int n;         // number of elements in bag

    public SDPBag() {
        first = null;
        n = 0;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size() {
        return n;
    }

    public void add(Item item) {
        ADTNode<Item> oldfirst = first;
        first = new ADTNode<Item>(item);
        first.next = oldfirst;
        n++;
    }
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private ADTNode<Item> current = first;

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
