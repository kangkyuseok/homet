package com.homet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homet.model.Mealkit;
import com.homet.model.Orders;
import com.homet.model.SetMenu;

@Mapper
public interface MealkitMapper {
	 List<Mealkit> getByCategory(String category);
	 Mealkit getByIdx(int midx);
	 void insertOrder(Orders order);
	 List<Orders> getOrdersByNickname(String nickname);
	 List<SetMenu> getSetByCategory(String category);
}
