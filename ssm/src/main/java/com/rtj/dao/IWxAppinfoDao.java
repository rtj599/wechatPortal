package com.rtj.dao;

import com.rtj.dto.WxAppinfo;


public interface IWxAppinfoDao {
    public WxAppinfo queryById(Integer id);  
    public void updateAccessToken(String appid,String accessToken);  

}
