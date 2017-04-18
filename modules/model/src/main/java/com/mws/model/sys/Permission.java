package com.mws.model.sys;

import com.mws.model.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 权限点
 *
 * @author xingkong1221
 * @date 2015-11-06
 */
@Entity
@Table(name = "sys_permission")
public class Permission extends IdEntity<Integer> {

	private static final long serialVersionUID = 6756911616895684581L;

	/** 名称 */
    private String name;

    /** 权限 */
    private String permission;

    /** 菜单 */
    private Menu menu;

    /** 角色 */
    private Set<Role> roles = new HashSet<Role>();

    /** 创建时间 */
    private Date createTime;

    /**
     * 实例化一个权限点
     */
    public Permission() {
    }

    /**
     * 获取权限点名称
     *
     * @return 权限点名称
     */
    @Column(length = 30)
    @NotBlank(message = "请输入权限点名称")
    @Length(max = 30, message = "权限点名称长度不能超过{max}个字符")
    public String getName() {
        return name;
    }

    /**
     * 设置权限点名称
     *
     * @param name 权限点名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取权限点
     *
     * @return 权限点
     */
    @Column(length = 30)
    @NotBlank(message = "请输入权限点名称")
    @Length(max = 30, message = "权限点名称不能超过{max}个字符")
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限点
     *
     * @param permission 权限点
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    public Menu getMenu() {
        return menu;
    }

    /**
     * 设置菜单
     *
     * @param menu 菜单
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * 获取角色
     *
     * @return 角色
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_permission",
            joinColumns = {@JoinColumn(name = "permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * 设置角色
     *
     * @param roles 角色
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
