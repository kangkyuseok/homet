package com.homet.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Videos {
	private int vidx;
	private String subject;
	private String category;
	private String url;
	private String content;
	private Date reg_date;
	private int read_cnt;
}
