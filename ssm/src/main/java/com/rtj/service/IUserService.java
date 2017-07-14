package com.rtj.service;

import java.util.List;

import javax.xml.registry.infomodel.User;

public interface IUserService {
    public User getUserById(int userId);  
    
    public void insertUser(User user);  
  
    public void addUser(User user);  
  
    public List<User> getAllUser();  
}
