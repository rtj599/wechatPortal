package com.rtj.framework.utils;

public class EncodeUtil {
	/**
	 *	字节数组转16进制
	 * @param byteArray
	 * @return
	 */
	public static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			 strDigest += byteToHexStr(byteArray[i]);
		}
		 return strDigest;
	}
	
	/**
	 * 单字节转16进制
	 * @param mByte
	 * @return
	 */
	public static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		return  new String(tempArr);
	}
}
