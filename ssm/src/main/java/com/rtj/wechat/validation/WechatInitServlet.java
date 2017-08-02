package com.rtj.wechat.validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import com.rtj.dao.IWxAppinfoDao;
import com.rtj.dto.WxAppinfo;
import com.rtj.framework.utils.EncodeUtil;
import com.rtj.framework.utils.PropertiesUtil;
/**
 * 微信后台接入校验
 * @description 
 * @author RTJ
 */
@Log4j
public class WechatInitServlet extends HttpServlet  {
	private static final long serialVersionUID = -2264756876008894216L;
	public static final String token="rtj599";
//	@Resource
//	private IWxAppinfoDao appInfoDao;
	@Override
	public void init()throws ServletException{}
	
	public void doGet(HttpServletRequest req,HttpServletResponse rsp) throws IOException{
	    log.info("RemoteAddr: "+ req.getRemoteAddr());  
		String signature=req.getParameter("signature");
		String time=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr = req.getParameter("echostr");  
		PrintWriter out = rsp.getWriter(); 
		//校验通过则将echostr原封不动返回
		if(checkSignature(signature,time,nonce)){
			log.info("校验成功！"+echostr);
			out.println(echostr);
		}else{
			log.info("校验失败！");
			out.println(echostr);
		}
		out.close();
	}
	private static boolean checkSignature(String sign,String time,String nonce){
		String params[]=new String[]{token,time,nonce};
		for(int j=0;j<params.length;j++){
			if(params[j]==null){
				return false;
			}
		}
		//1.字典排序
		Arrays.sort(params);
		//2.拼接
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<params.length;i++){
			sb.append(params[i]);
		}
		String mdStr=null;
		//3.加密
		try {
			MessageDigest md=MessageDigest.getInstance("SHA-1");
			byte[]digest=md.digest(sb.toString().getBytes());
			mdStr=EncodeUtil.byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb=null;
		//4.对比
		return mdStr==null?false:mdStr.equals(sign.toUpperCase());
		
	}

//	public static void main(String[] args) {
//		String testStr="15";
//		byte[]bytes=testStr.getBytes();
//		System.out.println(EncodeUtil.byteToStr(bytes));
//	}
}
