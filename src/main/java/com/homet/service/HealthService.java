package com.homet.service;

import java.util.List;

import com.homet.dao.HealthMapper;
import com.homet.model.Health;

public interface HealthService {
	
	public List<Health> healthList();
	
	
	public Health healthOne();


	public List<Health> healthSearch(String location);
	
	
}
