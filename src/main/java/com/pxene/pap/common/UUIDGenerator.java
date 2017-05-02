package com.pxene.pap.common;

import java.util.UUID;

/**
 * 修改UUID，由36位变成37位
 * @author lizhuoling
 *
 */
public class UUIDGenerator {
	 public static String getUUID() {  
		 String uuid = UUID.randomUUID().toString();  
	     //return uuid + "a";  
		 return uuid;
	 }  
}
