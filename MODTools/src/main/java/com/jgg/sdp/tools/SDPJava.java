package com.jgg.sdp.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SDPJava {

	public static Object executeMethod(Object o, String method) {
		String mName = method;
		String[] parms = null;
		
		int pos = method.indexOf('(');
		if (pos != -1) {
		   mName = method.substring(0,  pos);
		   String mParms = method.substring(pos + 1, method.length() - 1).trim();
		   if (mParms.length() > 0) parms = mParms.split(",");
		}
		try {
			Method m = o.getClass().getMethod(mName);
			m.setAccessible(true);
			if (parms == null) return m.invoke(o);
		} catch (NoSuchMethodException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

}
