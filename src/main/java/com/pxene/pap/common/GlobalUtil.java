package com.pxene.pap.common;


public class GlobalUtil {
	public static String parseString(String s, String v) {
	    if (s == null) {
	      return v;
	    }
	    return s;
    }

	public static String parseString(Object s, String v) {
		if (s == null) {
			return v;
		}
		return String.valueOf(s);
	}

	public static String parseStringAft(Object s, String v, String add) {
		if (s == null) {
			return v;
		}
		return String.valueOf(s) + add;
	}

	public static String parseStringBef(Object s, String v, String add) {
		if (s == null) {
			return v;
		}
		return add + String.valueOf(s);
	}

	public static Long parseLong(String s, Long v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Long.valueOf(Long.parseLong(s));
	  }

	public static Float parseFloat(String s, Float v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Float.valueOf(Float.parseFloat(s));
	}

	public static int parseInt(String s, int v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
	    return Integer.parseInt(s);
    }

	public static int parseInt(Object s, int v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Integer.parseInt(String.valueOf(s));
	}
	
}
