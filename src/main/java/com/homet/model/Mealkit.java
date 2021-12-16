package com.homet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mealkit {
	private int midx;
	private String name;
	private String price;
	private String image;
	private String category;
}
