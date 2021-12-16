package com.homet.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homet.dao.UserMapper;
import com.homet.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSessionTemplate userSqlSession;
	
	 private UserMapper dao;
	 
	 public UserServiceImpl(UserMapper dao) {
		 this.dao=dao;
	 }
	
	
	@Override
	public User login(User user) {
		return dao.login(user);
	}

	@Override
	public int insert(User user) {
		
		return dao.insert(user);
	}

	@Override
	public int update(User user) {
		return dao.update(user);
	}

	@Override
	public int delete(int uidx) {
		return dao.delete(uidx);
	}


	@Override
	public int idCheck(String email) {
		int cnt = dao.idCheck(email);
		return cnt;
	}
	@Override
	public String findId(User user) {
		
		return dao.findId(user);
	}


	@Override
	public int password(User user) {
		return dao.password(user);
		
	}
}