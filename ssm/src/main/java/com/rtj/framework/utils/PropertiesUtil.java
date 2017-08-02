package com.rtj.framework.utils;


public class PropertiesUtil  {

	
    public static synchronized String getPropertyWithArgs(String value,String[]args){
		for (int i = 0; i < args.length; i++) {
			value = value.replace("{" + i + "}", args[i]);
		}
		return value;
    }
}
