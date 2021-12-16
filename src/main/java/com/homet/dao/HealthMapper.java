package com.homet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homet.model.Health;

@Mapper
public interface HealthMapper {
	
    public List<Health> getList();
    
    public Health getOne();
    
    public List<Health> getSearch(String location);
}

//@Insert("INSERT INTO health(name,address) VALUES(#{name},#{address})")
//@Options(useGeneratedKeys = true, keyProperty ="hidx")
//int insert(@Param("health") Health health);

//@Select("SELECT * FROM health")
//@Results(id="HealthMap", value={
//	@Result(property="name", column="name"),
//	@Result(property="address",column="address")
//})
//List<Health> getAll();
//
//
//@Select("SELECT * FROM health WHERE hidx=#{hidx}")
//@ResultMap("HealthMap")
//Health getByID(@Param("hidx") int hidx);
//
//List<Health> healthList(int hidx);