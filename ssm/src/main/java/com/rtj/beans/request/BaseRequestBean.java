package com.rtj.beans.request;

import lombok.Data;

@Data
public class BaseRequestBean {
	//请求唯一标识，用于日志定位
	private String msgId;
}
