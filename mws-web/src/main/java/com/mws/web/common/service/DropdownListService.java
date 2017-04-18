package com.mws.web.common.service;

import com.mws.model.sys.Menu;
import com.mws.model.sys.Role;
import com.mws.web.common.bo.Constant;
import com.mws.web.common.bo.Option;
import com.mws.web.common.bo.TreeNode;
import com.mws.web.repository.MenuRepository;
import com.mws.web.repository.RoleRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DropdownListService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * 获取树形菜单列表
	 * 
	 * @return
	 */
	public List<TreeNode> getMenuTreeList() {
		List<TreeNode> list = Lists.newArrayList();
		Iterable<Menu> menus = menuRepository.findByParentMenuAndStatus(null, Constant.Status.Enable.value,
				new Sort(Direction.ASC, "sort"));
		if (null != menus) {
			for (Menu menu : menus) {
				TreeNode node = new TreeNode(menu.getId(), menu.getName(), menu.getParentMenu().getId());
				Set<Menu> children = menu.getChildMenu();
				if (null != children && children.size() > 0) {
					node.setIsParent(true);
					node.setOpen(true);
					node.setChildren(recursionMenuList(children));
				}
				list.add(node);
			}
		}
		return list;
	}

	/**
	 * 判断菜单ID是存在列表中
	 * 
	 * @param menuIds
	 * @return
	 */
	private boolean checkMenuIsExists(String menuIds, int menuId) {
		String[] ids = menuIds.split(",");
		for (String id : ids) {
			if (Integer.parseInt(id) == menuId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据用户角色获取树形菜单并初始化选中状态
	 * 
	 * @return
	 */
	public List<TreeNode> getMenuTreeList(Integer roleId) {
		Role role = roleRepository.findOne(roleId);
		String menuIds = role.getMenuIds();

		List<TreeNode> list = Lists.newArrayList();
		Iterable<Menu> menus = menuRepository.findByParentMenuAndStatus(null, Constant.Status.Enable.value,
				new Sort(Direction.ASC, "sort"));
		if (null != menus) {
			for (Menu menu : menus) {
				TreeNode node = new TreeNode(menu.getId(), menu.getName(), menu.getParentMenu().getId());
				Set<Menu> children = menu.getChildMenu();
				if (null != children && children.size() > 0) {
					node.setIsParent(true);
					node.setOpen(true);
					node.setChecked(checkMenuIsExists(menuIds, menu.getId()));
					node.setChildren(recursionMenuList(children, menuIds));
				} else {
					node.setChecked(checkMenuIsExists(menuIds, menu.getId()));
				}
				list.add(node);
			}
		}
		return list;
	}

	private List<TreeNode> recursionMenuList(Set<Menu> menus) {
		List<TreeNode> childrenTree = Lists.newArrayList();
		for (Menu menu : menus) {
			TreeNode node = new TreeNode(menu.getId(), menu.getName(), menu.getParentMenu().getId());
			Set<Menu> children = menu.getChildMenu();
			if (null != children && children.size() > 0) {
				node.setIsParent(true);
				node.setChildren(recursionMenuList(children));
			}
			childrenTree.add(node);
		}
		return childrenTree;
	}

	private List<TreeNode> recursionMenuList(Set<Menu> menus, String menuIds) {
		List<TreeNode> childrenTree = Lists.newArrayList();
		for (Menu menu : menus) {
			TreeNode node = new TreeNode(menu.getId(), menu.getName(), menu.getParentMenu().getId());
			Set<Menu> children = menu.getChildMenu();
			if (null != children && children.size() > 0) {
				node.setIsParent(true);
				node.setChecked(checkMenuIsExists(menuIds, menu.getId()));
				node.setChildren(recursionMenuList(children, menuIds));
			} else {
				node.setChecked(checkMenuIsExists(menuIds, menu.getId()));
			}
			childrenTree.add(node);
		}
		return childrenTree;
	}

	public List<Option> getPaymentList(String onlineId) {

		List<Option> options = Lists.newArrayList();
		options.add(new Option(1, "支付宝", false));
		options.add(new Option(2, "微信支付", false));
		options.add(new Option(3, "余额支付", false));
		options.add(new Option(4, "现金支付", false));
		for (Option option : options) {
			if (StringUtils.isNotBlank(onlineId)
					&& Integer.parseInt(onlineId) == Integer.parseInt(option.getValue().toString())) {
				option.setSelected("selected");
				break;
			}
		}
		return options;
	}

	/**
	 * 平台类型
	 * 
	 * @param type
	 * @return
	 */
	public List<Option> getPlatTypeList(String type) {

		List<Option> options = Lists.newArrayList();
		options.add(new Option(0, "全部", false));
		options.add(new Option(1, "微信", false));
		options.add(new Option(2, "IOS", false));
		options.add(new Option(3, "安卓", false));
		for (Option option : options) {
			if (StringUtils.isNotBlank(type)
					&& Integer.parseInt(type) == Integer.parseInt(option.getValue().toString())) {
				option.setSelected("selected");
				break;
			}
		}
		return options;
	}
}
