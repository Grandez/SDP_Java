package com.jgg.sdp.calculator;

import java.lang.reflect.*;

public class CalcJava {

	public static Object executeMethod(Object o, String method) {
		
		try {
			Method m = o.getClass().getMethod(method);
			return m.invoke(o);
			// return m.invoke(o, (Object[]) parms);
	    } catch (Exception e) {
//			throw new JavaException(e.getLocalizedMessage());
		}
		return null;
	}
}
