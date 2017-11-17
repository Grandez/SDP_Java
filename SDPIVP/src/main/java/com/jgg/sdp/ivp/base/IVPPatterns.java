package com.jgg.sdp.ivp.base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

public class IVPPatterns {

	public List<File> getFiles(String directory, String pattern) {
		String p = mountPattern(pattern);
		String d = (directory == null) ? "" : directory;
		if (d.length() > 0) return processDir(d, p);
		return processPattern(p);
	}
	
    private List<File> processDir(String dir, String pattern) {
    	ArrayList<File> list = new ArrayList<File>();
		URL root = Thread.currentThread().getContextClassLoader().getResource(dir);
		File base = new File(root.getPath());

		File[] files = base.listFiles();
		Arrays.sort(files);
		
		for (File f : files) {
			String name = f.getName();
//			if (name.matches(Pattern.quote(pattern))) list.add(f);
			Pattern pat = Pattern.compile(Pattern.quote(pattern));
			Matcher m = pat.matcher(f.getName());
			if (m.find()) list.add(f);
        }
		return list;
    }

    private List<File> processPattern(String pattern) {
    	ArrayList<File> list = new ArrayList<File>();
		Enumeration<URL> root = null;
		try {
			root = Thread.currentThread().getContextClassLoader().getResources(pattern);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (root.hasMoreElements()) {
			URL url = root.nextElement();
			File f = new File(url.getPath());
			if (f.getName().matches(pattern)) list.add(f);
		}
		return list;
    }
    
	private String mountPattern(String p) {
		if (p == null) return ".xml";
		if (p.endsWith(".xml")) return p;
		return p + ".xml";
	}
	
}
