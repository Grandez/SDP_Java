package com.jgg.sdp.tools;

import java.io.File;

public class FileNames {

	public static String getExtName(String input) {
		int pos = input.lastIndexOf('.');
		return (pos == -1) ? "" : input.substring(pos + 1);
	}

	public static String getBaseName(String input) {
		int ext = input.lastIndexOf('.');
		int dir = input.lastIndexOf(File.separatorChar);
		if (ext == -1) ext = input.length();
		return input.substring(dir +1, ext);
	}

	public static String getDirName(String input) {
		int dir = input.lastIndexOf(File.separatorChar);
		return (dir == -1) ? "" : input.substring(0, dir);
	}
	
}
