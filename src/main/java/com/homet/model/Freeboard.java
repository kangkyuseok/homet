package com.homet.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class Freeboard {

	private int fidx;
	private String subject;
	private String nickname;
	private String hashtag;
	private String content;
	private String fimage;
	private Date reg_date;
	private int like_cnt;
}
