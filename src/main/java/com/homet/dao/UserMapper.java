package com.homet.dao;

import org.apache.ibatis.annotations.Mapper;
import com.homet.model.User;


@Mapper
public interface UserMapper {
     User login(User user);
     int insert(User user);
     int update(User user);
     int delete(int uidx);
     int idCheck(String email);
     String findId(User user);
     int password(User user);
}