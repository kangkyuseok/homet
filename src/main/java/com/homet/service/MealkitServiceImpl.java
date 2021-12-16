package com.homet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.homet.dao.MealkitMapper;
import com.homet.model.Mealkit;
import com.homet.model.Orders;
import com.homet.model.SetMenu;

@Service
public class MealkitServiceImpl implements MealkitService {

	MealkitMapper dao;
	
	public MealkitServiceImpl(MealkitMapper dao) {
		this.dao=dao;
	}
	
	@Override
	public List<Mealkit> getByCategory(String category) {
		return dao.getByCategory(category); 
	}

	@Override
	public Mealkit getByIdx(int idx) {
		return dao.getByIdx(idx);
	}

	@Override
	public void insertOrder(Orders order) {
		dao.insertOrder(order);
	}

	@Override
	public List<Orders> getOrdersByNickname(String nickname) {
		return dao.getOrdersByNickname(nickname);
	}

	@Override
	public List<SetMenu> getSetByCategory(String category) {
		return dao.getSetByCategory(category);
	}

}
