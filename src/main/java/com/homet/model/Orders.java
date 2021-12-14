package com.homet.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
	private int odix;
	private String nickname;
	private String order_list;
	private Date order_date;
	private Date del_date;
}
