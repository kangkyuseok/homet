package com.homet.service;

import java.util.List;
import java.util.Map;

import com.homet.model.FPageDto;
import com.homet.model.Freeboard;

public interface FreeBoardService {

	int insert(Freeboard dto); 
	int update(Freeboard dto);  
	int delete(int fidx);
	Freeboard getBoardOne (int fidx);
	int getCount();
	List<Freeboard>getAll();  
	List<Freeboard> getPagelist(FPageDto dto);
	int searchCount(Map<String,Object> map);
	List<Freeboard> searchList(FPageDto pageDto);
	int like_cnt(int fidx);

	
}
