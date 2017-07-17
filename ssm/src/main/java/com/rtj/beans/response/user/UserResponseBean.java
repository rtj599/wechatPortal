package com.rtj.beans.response.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.rtj.beans.response.BaseResponseBean;
import com.rtj.dto.User;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserResponseBean extends BaseResponseBean{
	private List<User>userList;
}
