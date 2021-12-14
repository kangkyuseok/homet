package com.homet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMenu {
	private int setidx;
	private String name;
	private String content;
	private String price;
	private String idx_list;
	private String category;
}
