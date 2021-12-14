package com.homet.service;

import com.homet.model.User;

public interface UserService {
	 User login(User user);
     int insert(User user);
     int update(User user);
     int delete(int uidx);
     int idCheck(String email);
     String findId(User user);
     int password(User user);
     
}