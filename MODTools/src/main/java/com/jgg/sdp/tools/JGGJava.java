package com.jgg.sdp.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JGGJava {

	public static Object executeMethod(Object o, String method) {
		
		try {
			Method m = o.getClass().getMethod(method);
			m.setAccessible(true);
			return m.invoke(o);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
