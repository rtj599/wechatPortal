package com.rtj.controller;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rtj.beans.response.BaseResponseBean;
import com.rtj.constant.ReturnEnums;
import com.rtj.framework.annotation.Router;
import com.rtj.framework.annotation.SubTransType;
import com.rtj.framework.annotation.TransType;
import com.rtj.utils.SerializeUtil;

/**
 * 基础控制器
 * 根据注解和请求报文头的transType和subTransType决定调用类
 * @description 
 * @author RTJ
 */
@RestController
@Log4j
public class BaseController {
	
    public static ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");	
    
	@RequestMapping(method=RequestMethod.POST,value="/portal.do")
	public String dispatchRequest(HttpServletRequest req,HttpServletResponse rsp){
		String transType=req.getHeader("transType");
		String subTransType=req.getHeader("subTransType");
		Map<String,Object>routers=ac.getBeansWithAnnotation(Router.class);
		//根据TransType定位类
		for(Entry<String,Object> en:routers.entrySet()){
			Class<? extends Object> clazz=en.getValue().getClass();
			TransType trans=clazz.getAnnotation(TransType.class);
			//根据SubTransType调用方法
			if(trans.value().equals(transType)){
				Method[]methods=clazz.getDeclaredMethods();
				for(int i=0;i<methods.length;i++){
					Method m=methods[i];
					SubTransType subTrans=m.getAnnotation(SubTransType.class);
					if(subTrans.value().equals(subTransType)){
						try {
							return (String) m.invoke(ac.getBean("userController"), req, rsp);
						} catch (Exception e) {
							log.error(e.getMessage(),e);
							return errJsonResp(ReturnEnums.RETURN_ERROR_JSON);
						}
					}
				}
				break;
			}
		}
		return errJsonResp(ReturnEnums.RETURN_ERROR_NOTRANSTYPE);
	}
	
	private String errJsonResp(ReturnEnums returnMsg){
		BaseResponseBean rspBean=new BaseResponseBean();
		rspBean.setResponseCode(returnMsg.getCode());
		rspBean.setResponseDesc(returnMsg.getDesc());
		return SerializeUtil.serialize(rspBean);
	}
	
}
