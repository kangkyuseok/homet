package com.homet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homet.dao.HealthMapper;
import com.homet.model.Health;

@Service
public class HealthServiceImpl implements HealthService{
	
	HealthMapper hdao;
	
	public HealthServiceImpl(HealthMapper hdao) {
		this.hdao=hdao;
	}
	
	@Override
	public List<Health> healthList() {
		return hdao.getList();
	}
	
	@Override
	public Health healthOne() {
		return hdao.getOne();
	}
	
	// 11-18 작업
	@Override
	public List<Health> healthSearch(String location){
		return hdao.getSearch(location);
	}
	
	
	
}
