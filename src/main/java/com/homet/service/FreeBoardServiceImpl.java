package com.homet.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homet.dao.FreeboardMapper;
import com.homet.model.FPageDto;
import com.homet.model.Freeboard;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	@Autowired
	FreeboardMapper dao;
	
	public void FreeboardMapperImpl(FreeboardMapper dao){
		this.dao = dao;
		}
	
	@Override
	public int insert(Freeboard dto) {
		return dao.insert(dto);
	}

	@Override
	public int update(Freeboard dto) {
		return dao.update(dto);
	}

	@Override
	public int delete(int fidx) {
		return dao.delete(fidx);
	}

	@Override
	public Freeboard getBoardOne(int fidx) {
		return dao.getBoardOne(fidx);
	}

	@Override
	public int getCount() {
		return dao.getCount();
	}


	@Override
	public List<Freeboard> getAll() {
		return dao.getAll();
	}
	
	@Override
	public List<Freeboard> getPagelist(FPageDto dto) {
		return dao.getPagelist(dto);
	}

	@Override
	public int searchCount(Map<String, Object> map) {
		return dao.searchCount(map);
	}

	@Override
	public List<Freeboard> searchList(FPageDto dto) {
		return dao.searchList(dto);
	}
	

	@Override
	public int like_cnt(int fidx) {
		// TODO Auto-generated method stub
		return dao.like_cnt(fidx);
	}
	
	
	
	

}
