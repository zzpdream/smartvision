package com.mws.web.net.dto;

/**
 * Created by ranfi on 3/7/16.
 */
public class VoteQueryItemDto {

    private Integer id;
    private String name;
    private Integer key;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
