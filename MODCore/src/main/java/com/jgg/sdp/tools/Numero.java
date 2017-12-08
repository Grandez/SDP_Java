package com.jgg.sdp.tools;

public class Numero {

	public static boolean isANumber(String s) {
       return isANumber(s,10);
    }

    public static boolean isANumber(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
               if(s.length() == 1) return false;
               else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    public static boolean isInteger(Object value) {
    	try {
    		Integer.parseInt(value.toString());
    	}
    	catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
    }
    
    public static Integer makeInteger(Object value) {
    	return Integer.parseInt(value.toString());
    }

    public static Long makeLong(Object value) {
    	return Long.parseLong(value.toString());
    }

}
