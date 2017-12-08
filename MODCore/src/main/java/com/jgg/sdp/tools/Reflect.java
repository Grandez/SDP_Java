package com.jgg.sdp.tools;

import java.io.IOException;
import java.lang.reflect.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.*;
import org.springframework.core.type.classreading.*;
import org.springframework.util.*;

import com.jgg.sdp.core.exceptions.JavaException;

public class Reflect {

	public static String findModule(String name) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resolver);
		String basePath = ClassUtils.convertClassNameToResourcePath("com.jgg.sdp");
		Resource[] resources;
		
		// Solo deberia haber una clase, si hay varias, recojo la primera
		try {
		    resources = resolver.getResources("classpath*:" + basePath + "/**/" + name + ".class");
			if (resources.length == 0) return null;
			MetadataReader reader = readerFactory.getMetadataReader(resources[0]);
			return reader.getClassMetadata().getClassName();
		} catch (IOException e) {
		    return null;
		}
	}

	public static Object executeJavaMain(String className, String[] parms) {
		try {
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getMethod("main", String[].class);
	    	Object[] args = new Object[1];
	        args[0] = parms;
			return method.invoke(null, args);
		} catch (Exception e) {
			throw new JavaException(e.getLocalizedMessage());
		}

	}
	
	public static Object executeMethodStatic(String className, String methodName, Class<?>... parameterTypes) {
		try {
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getMethod(methodName, String[].class);
			return method.invoke(null, (Object[]) parameterTypes);
		} catch (Exception e) {
			throw new JavaException(e.getLocalizedMessage());
		}

	}
	
	//JGG Por ahora no soporto sobrecarga de metodos
	public static Object executeMethod(Object o, String method, String[] parms) {
		Method m = findMethod(o, method, parms);
		if (m == null) {
			throw new JavaException(1, method, o.getClass().getSimpleName());
		}
		try {
			if (parms == null || parms[0] == null) return m.invoke(o);
			return m.invoke(o, (Object[]) parms);
	    } catch (Exception e) {
			throw new JavaException(e.getLocalizedMessage());
		}
	}
	
	private static Method findMethod(Object o, String method, String[] parms) {
//		ArrayList<Method> lst = new ArrayList<Method>(); 
		Method[] methods = o.getClass().getMethods();
		for (int idx = 0; idx < methods.length; idx++) {
			if (methods[idx].getName().compareTo(method) == 0) {
				return methods[idx];
			}
		}
		return null;
	}
	
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
		} catch (Exception e) {
			throw new JavaException(e.getLocalizedMessage());
		}
		return null;
	}

	public static Object executeInstance(String classBase, String method, String[] parms) {
		try {
		   String className = findModule(classBase);
		   Class<?> clazz = Class.forName(className);
		   Constructor<?> ctor = clazz.getConstructor();
		   Object obj = ctor.newInstance();
		   if (parms == null || parms.length == 0) return executeMethod(obj, method);
		   return executeMethod(obj, method, parms);
		}
		catch (Exception e) {
			throw new JavaException(e.getLocalizedMessage());
		}
	}
	
}
