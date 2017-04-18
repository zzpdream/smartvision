package com.mws.web.common.controller;

import com.mws.web.common.bo.TreeNode;
import com.mws.web.common.service.DropdownListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/dropdown")
public class DropDownController {

	@Autowired
	private DropdownListService dropdownListService;

	/**
	 * 获取功能菜单树形结构
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/menuTree")
	@ResponseBody
	public List<TreeNode> getMenuTreeList(HttpServletRequest request) {
		return dropdownListService.getMenuTreeList();
	}

	/**
	 * 获取功能菜单树形结构，并根据用户角色初始化选中菜单项
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/menuTree/{roleId}")
	@ResponseBody
	public List<TreeNode> getMenuTreeList(@PathVariable("roleId") Integer roleId, HttpServletRequest request) {
		return dropdownListService.getMenuTreeList(roleId);
	}
}
