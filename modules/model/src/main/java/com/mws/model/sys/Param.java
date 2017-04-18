package com.mws.model.sys;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_param database table.
 * 
 */
@Entity
@Table(name="sys_param")
public class Param implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/**
	 * 键
	 */
	@Column(unique = true, name="`key`")
	private String key;

	/**
	 * 配置描述
	 */
	@Column(name="param_desc")
	private String paramDesc;

	/**
	 * 值
	 */
	private String value;

    public Param() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}