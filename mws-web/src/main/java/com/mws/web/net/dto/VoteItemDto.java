package com.mws.web.net.dto;

/**
 * Created by ranfi on 3/7/16.
 */
public class VoteItemDto {

	private Integer id;
	private String item;
	private Integer value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
