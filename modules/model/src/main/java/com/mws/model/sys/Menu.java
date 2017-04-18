package com.mws.model.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity - 菜单
 */
@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort = 3;

    /**
     * 菜单备注说明
     */
    private String remark;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单状态
     */
    private Integer status = 1;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 菜单关联的角色
     */
    private Set<Role> roles = new HashSet<Role>();

    /**
     * 父菜单
     */
    private Menu parentMenu;

    /**
     * 子菜单
     */
    private Set<Menu> childMenu = new HashSet<Menu>();

    /**
     * 权限点
     */
    private Set<Permission> permissions = new HashSet<Permission>();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 实例化一个菜单对象
     */
    public Menu() {
    }

    public Menu(int id, String name, String url, String icon, Menu parentMenu) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.parentMenu = parentMenu;
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 实例化一个菜单对象
     *
     * @param id 菜单编号
     */
    public Menu(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单创建时间
     *
     * @return 菜单创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置菜单创建时间
     *
     * @param createTime 菜单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取排序值
     *
     * @return 排序值
     */
    @Column(name = "`sort`", nullable = false)
    public Integer getSort() {
        return this.sort;
    }

    /**
     * 设置排序值
     *
     * @param sort 排序值
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取菜单备注说明
     *
     * @return 菜单备注说明
     */
    @Length(max = 500, message = "菜单备注说明长度不能超过{max}个字符")
    @Column(name = "remark", length = 500)
    public String getRemark() {
        return remark;
    }

    /**
     * 设置菜单备注说明
     *
     * @param remark 菜单备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @NotBlank(message = "请输入菜单名称")
    @Length(max = 45, message = "菜单名称长度不能超过{0}个字符")
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单状态<br/>
     * 1: 可用  0: 禁用
     *
     * @return 菜单状态
     */
    @Column(name = "status")
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置菜单状态<br/>
     * 1: 可用  0: 禁用
     *
     * @param status 菜单状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取菜单链接地址
     *
     * @return 菜单链接地址
     */
    @Length(max = 100, message = "链接地址长度不能超过{0}个字符")
    @Column(name = "url", nullable = true)
    public String getUrl() {
        return this.url;
    }

    /**
     * 设置菜单链接地址
     *
     * @param url 菜单链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取父菜单
     *
     * @return 父菜单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    public Menu getParentMenu() {
        return parentMenu;
    }

    /**
     * 设置父菜单
     *
     * @param parentMenu 父菜单
     */
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * 获取菜单关联的角色
     *
     * @return 菜单关联的角色
     */
    @ManyToMany(mappedBy = "menus")
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * 设置菜单关联的角色
     *
     * @param roles 菜单关联的角色
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * 获取子菜单
     *
     * @return 子菜单
     */
    @OneToMany(mappedBy = "parentMenu", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @OrderBy("sort ASC")
    @NotFound(action = NotFoundAction.IGNORE)
    public Set<Menu> getChildMenu() {
        return childMenu;
    }

    /**
     * 获取子菜单
     *
     * @param childMenu 子菜单
     */
    public void setChildMenu(Set<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    /**
     * 获取菜单图标
     *
     * @return 菜单图标
     */

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取权限点
     *
     * @return 权限点
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}