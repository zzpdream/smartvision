package com.mws.web.common.bo;

public class Option {

	private Object value;
	private String text;
	private String selected;
	private String extraInfo;

	public Option() {

	}

	public Option(Object value, String text, Boolean selected) {
		this.value = value;
		this.text = text;
		this.selected = selected ? "selected" : "";
	}

	public Option(Object value, String text, Boolean selected, String extraInfo) {
		this.value = value;
		this.text = text;
		this.selected = selected ? "selected" : "";
		this.extraInfo = extraInfo;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

}
