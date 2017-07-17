package com.rtj.service;

import java.util.List;

import com.rtj.dto.User;


public interface IUserService {
    public User getUserById(int userId);  
    
    public void insertUser(User user);  
  
    public void deleteUser(int userId);  
  
    public List<User> getAllUser();  
    
    public void updateUser(User user);
}
