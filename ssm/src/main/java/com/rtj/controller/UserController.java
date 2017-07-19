package com.rtj.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.rtj.beans.response.user.UserResponseBean;
import com.rtj.constant.ReturnEnums;
import com.rtj.dto.User;
import com.rtj.framework.annotation.Router;
import com.rtj.framework.annotation.SubTransType;
import com.rtj.framework.annotation.TransType;
import com.rtj.framework.utils.SerializeUtil;
import com.rtj.service.IUserService;


/**
 * @description  User控制器
 * 		RestController注解--代替每一个mapping的@ResponseBody(spring 4+)
 * 		并添加AsyncRestTemplate 属性,支持REST异步无阻塞通信
 * @author RTJ
 */
@Component
@Router
@TransType("user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@SubTransType("userList")
	public String userList (HttpServletRequest req,HttpServletResponse rsp){
        List<User> uList = userService.getAllUser();  
        UserResponseBean rspBean=new UserResponseBean();
        rspBean.setUserList(uList);
        rspBean.setResponseCode(uList.size()==0?ReturnEnums.RETURN_SUCCESS_NODATA.getCode():ReturnEnums.RETURN_SUCCESS.getCode());
        rspBean.setResponseDesc(uList.size()==0?ReturnEnums.RETURN_SUCCESS_NODATA.getDesc():ReturnEnums.RETURN_SUCCESS.getDesc());
		return SerializeUtil.serialize(rspBean);

	}
}
