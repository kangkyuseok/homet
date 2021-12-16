package com.homet.service;

import java.util.List;
import java.util.Map;

import com.homet.model.PageDto;
import com.homet.model.Videos;

public interface VideosService {
	List<Videos> getAll();
	Videos getOne(int vidx);
	int categoryCount(Videos video);
	List<Videos> getPageList(PageDto dto);
	int listCount(Map<String,Object> map);
	int insert(Videos video);
	int update(Videos video);
	int updateReadCnt(int vidx);
	int delete(int vidx);
}
