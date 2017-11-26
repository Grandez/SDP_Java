package com.jgg.sdp.adt;

import java.util.*;

/**
 * HashMap con duplicados
 * @author Javier
 *
 */
public class ADTHashDup<K,V> {

	private HashMap<K, ArrayList<V>> map = new HashMap<K, ArrayList<V>>(); 
	
	public boolean addItem(K k, V v) {
		ArrayList<V> l = map.get(k);
		if (l == null) {
			l = new ArrayList<V>();
			l.add(v);
			map.put(k, l);
			return false;
		}
		l.add(v);
		return true;
	}
	
	public V getItem(K k) {
		ArrayList<V> l = map.get(k);
		if (l == null) return null;
		return l.get(0);
	}

	public List<V> getItemList(K k) {
		return map.get(k); 
	}
	
	public int numItems(K k) {
		ArrayList<V> l = map.get(k);
       	return (l == null) ? 0 : l.size();
	}
}
