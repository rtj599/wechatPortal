package com.rtj.wechat.loop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

import com.rtj.dao.IWxAppinfoDao;
import com.rtj.dto.WxAppinfo;
import com.rtj.framework.utils.PropertiesUtil;

@Log4j
@Component
public class GetAccessToken {
	@Resource
	private IWxAppinfoDao appInfoDao;
	@Value(value = "${WECHAT_APP_ID}")
	private String appid;
	@Value(value = "${WECHAT_API_URL}")
	private String origUrl;
	@Value(value = "${WECHAT_GRANT_TYPE_CLIENT}")
	private String grantType;
	
	@PostConstruct
	public void getAccessToken(){
		ScheduledExecutorService  exe=Executors.newScheduledThreadPool(1);
		exe.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				log.info("定时获取ACCESS TOKEN任务启动！");
				WxAppinfo info=appInfoDao.queryById(1);
				String secret=info.getAppSecret();
				String args[]=new String[]{grantType,appid,secret};
				String url=PropertiesUtil.getPropertyWithArgs(origUrl, args);
				System.out.println(url);
			}
			
		},0,30,TimeUnit.SECONDS);
	
	}
}
