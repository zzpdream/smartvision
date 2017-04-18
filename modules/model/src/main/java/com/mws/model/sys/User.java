package com.mws.model.sys;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the sys_user database table.
 */
@Entity
@Table(name = "sys_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "status"})
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 登录帐号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 全名
     */
    private String fullname;

    /**
     * 性别
     */
    private byte gender;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态
     */
    private byte status;

    /**
     * 删除状态
     */
    private boolean deleted = false;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 角色
     */
    private List<Role> roleList = new ArrayList<Role>(); // 有序的关联对象集合

    /**
     * 实例化一个用户
     */
    public User() {
    }

    public User(String account, String password, String salt) {
        this.account = account;
        this.password = password;
        this.salt = salt;
    }

    /**
     * 实例化一个用户
     *
     * @param id 用户编号
     */
    public User(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户编号
     *
     * @return 用户编号
     */
    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置用户编号
     *
     * @param id 用户编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取登录帐号
     *
     * @return 登录帐号
     */
    @Column(length = 45)
    @NotBlank(message = "请输入登陆帐号")
    @Length(max = 45, message = "帐号长度不能超过{max}个字符")
    public String getAccount() {
        return this.account;
    }

    /**
     * 设置登录帐号
     *
     * @param account 登录帐号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取邮箱地址
     *
     * @return 邮箱地址
     */
    @NotBlank(message = "请输入邮箱地址")
    @Column(name = "email", length = 45)
    @Length(max = 45, message = "邮箱地址长度不能超过{max}个字符")
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置邮箱地址
     *
     * @param email 邮箱地址
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取全名
     *
     * @return 全名
     */
    @Column(name = "fullname", length = 45)
    @Length(max = 45, message = "姓名长度不能超过{max}个字符")
    public String getFullname() {
        return this.fullname;
    }

    /**
     * 设置全名
     *
     * @param fullname 全名
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    @Column(name = "gender")
    public byte getGender() {
        return this.gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(byte gender) {
        this.gender = gender;
    }

    /**
     * 获取昵称
     *
     * @return 昵称
     */
    @NotBlank(message = "请输入用户昵称")
    @Column(name = "nickname", length = 50)
    @Length(max = 50, message = "昵称长度不能超过{max}个字符")
    public String getNickname() {
        return this.nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取密码(加密后)
     *
     * @return 密码
     */
    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取手机号码
     *
     * @return 手机号码
     */
    @Column(name = "phone")
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "(0|86|17951)?(13[0-9]|15[012356789]|17[012356789]|18[012356789]|14[57])[0-9]{8}",
            message = "手机号码格式不正确")
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取盐
     *
     * @return 盐
     */
    @Column(name = "salt")
    public String getSalt() {
        return this.salt;
    }

    /**
     * 设置盐
     *
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }


    /**
     * 获取状态
     *
     * @return 状态
     */
    @Column(name = "status")
    public byte getStatus() {
        return this.status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    /**
     * 获取删除状态
     *
     * @return 删除状态
     */
    @Type(type = "yes_no")
    public boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置删除状态
     *
     * @param deleted 删除状态
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取用户关联的角色
     *
     * @return 关联的角色
     */
    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    public List<Role> getRoleList() {
        return roleList;
    }

    /**
     * 设置用户关联的角色
     *
     * @param roleList 关联的角色
     */
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 判断当前用户是否包含选中的角色
     *
     * @param roleId 角色编号
     * @return 包含返回true，反之返回false
     */
    @Transient
    public boolean hasRoleChecked(Integer roleId) {
        if (roleId != null && roleId > 0 && CollectionUtils.isNotEmpty(roleList)) {
            for (Role role : roleList) {
                if (roleId.equals(role.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}