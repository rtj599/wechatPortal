package com.rtj.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtj.beans.response.user.UserResponseBean;
import com.rtj.constant.ReturnEnums;
import com.rtj.dto.User;
import com.rtj.service.IUserService;
import com.rtj.utils.SerializeUtil;


/**
 * @description  User控制器
 * 		RestController注解--代替每一个mapping的@ResponseBody(spring 4+)
 * 		并添加AsyncRestTemplate 属性,支持REST异步无阻塞通信
 * @author RTJ
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/userList")
	public String userList (HttpServletRequest req){
        List<User> uList = userService.getAllUser();  
        UserResponseBean rsp=new UserResponseBean();
        rsp.setUserList(uList);
        rsp.setResponseCode(uList.size()==0?ReturnEnums.RETURN_SUCCESS_NODATA.getCode():ReturnEnums.RETURN_SUCCESS.getCode());
        rsp.setResponseDesc(uList.size()==0?ReturnEnums.RETURN_SUCCESS_NODATA.getDesc():ReturnEnums.RETURN_SUCCESS.getDesc());
		return SerializeUtil.serialize(rsp);

	}
}
