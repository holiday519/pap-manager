package com.pxene.pap.service;

import java.util.UUID;

/**
 * 修改UUID，由36位变成37位
 * @author lizhuoling
 *
 */
public class UUIDGenerator {
	 public UUIDGenerator() { }  	  
	 public static String getUUID() {  
		 UUID uuid = UUID.randomUUID();  
		 String strUUID = uuid + "a".toString();  	      
	     return strUUID;  
	 }  
}
