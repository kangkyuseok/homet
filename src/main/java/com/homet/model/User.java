package com.homet.model;

import lombok.AllArgsConstructor; 
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Builder
public class User {
	private int uidx;
	private String name;
	
	private String nickname;
	private String email;
	private String password;
	private  int age;
	private String gender;
	private String addr;
	private String kakao;
}