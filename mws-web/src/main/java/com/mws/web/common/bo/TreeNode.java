package com.mws.web.common.bo;

import java.util.List;

/**
 * 
 * @ClassName: TreeNode
 * @Description: 定义z Tree数据结构
 * @author: ranfi
 * @date: Mar 20, 2014 12:52:37 PM
 * 
 */
public class TreeNode {

	private Integer id;
	private String name;
	private String title;
	private String url;
	private Integer pId;
	private String icon;
	private Boolean isParent = false;
	private Boolean open = false;
	private Boolean oncheck = true;
	private Boolean checked = false;

	private List<TreeNode> children;

	public TreeNode() {

	}

	public TreeNode(Integer id, String name, Integer pid) {
		this.id = id;
		this.name = name;
		this.pId = pid;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getOncheck() {
		return oncheck;
	}

	public void setOncheck(Boolean oncheck) {
		this.oncheck = oncheck;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
