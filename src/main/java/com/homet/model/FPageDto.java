package com.homet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FPageDto {
	//생성자로 전달받을값
	private int currentPage; 	//현재페이지
	private int pageSize;		//한 화면에 띄울 게시글 limit 범위
	private int totalCount;		//전체 게시글
	
	//계산에 필요한 값
	private int startNo;		//limit 시작 인덱스
	private int totalPage;		//전체 페이지 개수       처음 <1 2 3> 끝
	private int startPage;		//페이지 안에 띄울 게시글 시작 idx
	private int endPage;		//페이지 안에 띄울 게시글 끝 idx
	
	//검색에 필요한 값
	private String field;
	private String findText;
	
	//생성자
	public FPageDto(int currentPage, int pageSize, int totalCount, String field, String findText) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		totalPage = (totalCount-1)/pageSize +1; 
		
		this.currentPage = (currentPage>totalPage || currentPage < 1)? 1:currentPage; 
		startNo = (currentPage-1) * pageSize;
		
		startPage = (this.currentPage - 1) / 10 * 10 + 1;  
		endPage = startPage + pageSize-1;						
		endPage = endPage > totalPage ? totalPage : endPage;  
		
		this.field = field;
		this.findText = findText;
	}

	
}
