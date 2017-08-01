package com.rtj.dto;

import lombok.Data;

@Data
public class WxAppinfo {
	private Integer id;
	private String appId;
	private String appSecret;
	private String accessToken;
}
