package com.homet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homet.model.Likes;
import com.homet.model.PageDto;

@Mapper
public interface LikesMapper {

	int insert(Likes dto);
	int delete(Likes dto);
	List<Likes>getList(PageDto dto);
	int selectByFidx(int fidx);
	int selectByNickname(String nickname);
	int selectByNicknameFidx(Likes dto);
}
