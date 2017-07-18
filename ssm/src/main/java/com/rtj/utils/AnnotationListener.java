//package com.rtj.utils;
//
//import java.util.Map;
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import com.rtj.framework.annotation.Router;
//
//@Component
//public class AnnotationListener implements ApplicationListener<ContextRefreshedEvent>{
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent ev) {
//		Map<String,Object>beans=ev.getApplicationContext().getBeansWithAnnotation(Router.class);
//	}
//
//}
