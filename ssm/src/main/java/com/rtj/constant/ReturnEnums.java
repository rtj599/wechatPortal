package com.rtj.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码枚举类
 * @param code
 * @param desc
 */
@AllArgsConstructor
public enum ReturnEnums {
	RETURN_SUCCESS("0000","成功"),
	RETURN_SUCCESS_NODATA("0001","未查询到数据"),
	RETURN_ERROR_NOTRANSTYPE("9991","未找到请求报文指定的服务"),
	RETURN_ERROR_JSON("9992","报文解析出错"),
	RETURN_ERROR_UNKNOWN("9999","意外出错");
	@Getter
	String code;
	@Getter
	String desc;
}
