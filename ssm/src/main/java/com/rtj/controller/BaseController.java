package com.rtj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 基础控制器
 * @description 
 * @author RTJ
 */
@Controller
public class BaseController {
	@RequestMapping(method=RequestMethod.POST,value="/portal.do")
	public String dispatchRequest(HttpServletRequest req,HttpServletResponse rsp){
		String transType=req.getHeader("transType");
		String subTransType=req.getHeader("subTransType");
		return "redirect:/"+transType+"/"+subTransType;
	}
}
