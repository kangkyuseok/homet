package com.homet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.homet.model.FPageDto;
import com.homet.model.Freeboard;


@Mapper
public interface FreeboardMapper {

	int insert(Freeboard dto);                  //insert
	int update(Freeboard dto);                  //update
	int delete(int fidx);                       //delete
	Freeboard getBoardOne (int fidx);           //한 개 검색
	int getCount();	                            //전체 글 개수
	List<Freeboard>getAll();                    //전체 게시글
	List<Freeboard> getPagelist(FPageDto dto);   //한 페이지 게시글
	int searchCount(Map<String,Object> map);	//검색 카운트
	List<Freeboard> searchList(FPageDto dto);	//검색 게시글
	int like_cnt(int fidx);
}
