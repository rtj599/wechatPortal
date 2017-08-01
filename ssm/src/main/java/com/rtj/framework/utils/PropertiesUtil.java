package com.rtj.framework.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtil  {

	
    public static synchronized String getPropertyWithArgs(String value,String[]args){
		for (int i = 0; i < args.length; i++) {
			value = value.replace("{" + i + "}", args[i]);
		}
		return value;
    }
}
