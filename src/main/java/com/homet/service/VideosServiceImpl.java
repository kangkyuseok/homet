package com.homet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homet.dao.VideosMapper;
import com.homet.model.PageDto;
import com.homet.model.Videos;

@Service
public class VideosServiceImpl implements VideosService {

	@Autowired
	VideosMapper dao;
	
	@Override
	public List<Videos> getAll() {
		return dao.getAll();
	}

	@Override
	public Videos getOne(int vidx) {
		return dao.getOne(vidx);
	}
	
	@Override
	public int categoryCount(Videos video) {
		return dao.categoryCount(video);
	}

	@Override
	public List<Videos> getPageList(PageDto dto) {
		return dao.getPageList(dto);
	}
	
	@Override
	public int listCount(Map<String,Object> map) {
		return dao.listCount(map);
	}

	@Override
	public int insert(Videos video) {
		return dao.insert(video);
	}

	@Override
	public int update(Videos video) {
		return dao.update(video);
	}

	@Override
	public int updateReadCnt(int vidx) {
		return dao.updateReadCnt(vidx);
	}
	
	@Override
	public int delete(int vidx) {
		return dao.delete(vidx);
	}

}
