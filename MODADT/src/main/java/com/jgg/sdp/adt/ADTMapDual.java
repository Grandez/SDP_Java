package com.jgg.sdp.adt;

import java.util.*;

public class ADTMapDual<K,V, O> {

	private ArrayList<O> objects = new ArrayList<O>();
	private HashMap<K, Integer> map1 = new HashMap<K, Integer>();
	private HashMap<V, Integer> map2 = new HashMap<V, Integer>();
	
	public O add(K key1, V key2, O o) {
		Integer pos = map1.get(key1);
		if (pos == null) {
			objects.add(o);
			pos = objects.size() - 1;
			map1.put(key1,  pos);
			map2.put(key2,  pos);
		}
		else {
			objects.set(pos, o);
		}
		return o;
	}
	
	@SuppressWarnings({ "unchecked", "finally" })
	public O get(Object key) {
		Integer pos = null;
		try {
		    pos = map1.get((K) key);
		}
		catch (Exception e) {
		    pos = map2.get((V) key);
		}
		finally {
		    return (pos == null) ? null : objects.get(pos);
		}			
	}
	
	
	
}
