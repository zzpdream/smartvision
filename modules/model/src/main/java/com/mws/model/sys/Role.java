package com.mws.model.sys;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.mws.model.IdEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity - 角色
 */
@Entity
@Table(name = "sys_role")
public class Role extends IdEntity<Integer> {

	private static final long serialVersionUID = 1L;

	/** 角色名称 */
	private String name;

	/** 创建用户 */
	private User user;

	/** 菜单集合 */
	private List<Menu> menus;

	/** 用户集合 */
	private Set<User> users = new HashSet<User>();

	/** 权限点 */
	private List<Permission> permissions;

	/** 创建时间 */
	private Timestamp createTime;

	/**
	 * 实例化一个角色
	 */
	public Role() {
	}

	/**
	 * 获取名称
	 *
	 * @return 名称
	 */
	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	/**
	 * 设置名称
	 *
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取创建时间
	 *
	 * @return 创建时间
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建四件
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取创建用户
	 *
	 * @return 用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_uid", referencedColumnName = "uid", insertable = false, updatable = false)
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 * 设置创建用户
	 *
	 * @param user 用户
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取菜单集合
	 *
	 * @return 菜单集合
	 */
	@ManyToMany
	@JoinTable(name = "sys_role_menu", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "menu_id", referencedColumnName = "id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	public List<Menu> getMenus() {
		return menus;
	}

	/**
	 * 设置菜单集合
	 *
	 * @param menus 菜单集合
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	/**
	 * 获取包含此角色的用户
	 *
	 * @return 包含此角色的用户
	 */
	@ManyToMany
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "uid", referencedColumnName = "uid") })
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * 设置包含此角色的用户
	 *
	 * @param users 包含此角色的用户
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * 获取角色关联的权限点
	 *
	 * @return 角色关联的权限点
	 */
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "sys_role_permission",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "permission_id")})
	public List<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * 设置角色关联的权限点
	 *
	 * @param permissions 角色关联的权限点
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Transient
	public String getMenuIds() {
		String menuIds = "";
		for (Menu menu : menus) {
			menuIds += String.valueOf(menu.getId()) + ",";
		}
		if (!StringUtils.isEmpty(menuIds)) {
			return menuIds.substring(0, menuIds.lastIndexOf(","));
		} else {
			return "";
		}
	}

	/**
	 * 获取角色的权限点
	 *
	 * @return 权限点
	 */
	@Transient
	public List<String> getPermissionList() {
		List<String> permissionList = new ArrayList<String>();
		for (Permission permission : permissions) {
			permissionList.add(permission.getPermission());
		}
		return permissionList;
	}

	/**
	 * 判断当前角色是否包含选中的菜单
	 *
	 * @param menuId 菜单编号
	 * @return 包含返回true，反之false
	 */
	@Transient
	public boolean hasMenuChecked(Integer menuId) {
		if (menuId != null && menuId > 0 && CollectionUtils.isNotEmpty(menus)) {
			for (Menu menu : menus) {
				if (menu.getId() == menuId) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断当前角色是否包含选中的权限点
	 *
	 * @param permissionId 权限点编号
	 * @return 包含返回true，反之false
	 */
	@Transient
	public boolean hasPermissionChecked(Integer permissionId) {
		if (permissionId != null && permissionId > 0 && CollectionUtils.isNotEmpty(permissions)) {
			for (Permission permission : permissions) {
				if (permission.getId() ==  permissionId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}