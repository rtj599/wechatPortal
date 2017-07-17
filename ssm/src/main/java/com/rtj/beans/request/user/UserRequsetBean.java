package com.rtj.beans.request.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.rtj.beans.request.BaseRequestBean;
@Data
@EqualsAndHashCode(callSuper=true)
public class UserRequsetBean extends BaseRequestBean{
	private int id;
	private String name;
	private String password;
	private int age;
}
