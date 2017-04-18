/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.mws.web.shiro;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import com.mws.web.common.bo.Constant;
import com.mws.web.context.ParameterCache;
import com.mws.web.service.AccountService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.google.common.base.Objects;
import com.mws.core.utils.EncodeUtils;
import com.mws.model.sys.Role;
import com.mws.model.sys.User;

public class ShiroDbRealm extends AuthorizingRealm {
	
	
	static Logger logger = Logger.getLogger(ShiroDbRealm.class);
	
    protected AccountService accountService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = accountService.findUserByAccount(token.getUsername());
        ShiroUser shiroUser = null;
		 if (user != null) {
            if (Constant.Status.DISABLE.value == user.getStatus()) {
                throw new DisabledAccountException();
            }
            
            if (user.getDeleted() == true) {
                throw new UnknownAccountException();
            }
            byte[] salt = EncodeUtils.decodeHex(user.getSalt());
            shiroUser = new ShiroUser(user.getId(), user.getAccount(), user.getNickname());
            return new SimpleAuthenticationInfo(shiroUser,user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            //判断是否是超级管理员
            if (accountService.isSupervisor(token.getUsername())) {
                String salt = ParameterCache.getSystemProp("system.admin.salt");
                String password = ParameterCache.getSystemProp("system.admin.password");
                String name = ParameterCache.getSystemProp("system.admin.name");
                user = new User(token.getUsername(), password, salt);
                user.setId(0);
                user.setNickname(name);
                byte[] decodeSalt = EncodeUtils.decodeHex(salt);
                shiroUser = new ShiroUser(user.getId(), user.getAccount(), user.getNickname());
                return new SimpleAuthenticationInfo(shiroUser,
                        user.getPassword(), ByteSource.Util.bytes(decodeSalt), getName());
            }
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = accountService.findUserByAccount(shiroUser.account);
        System.out.println("debug 333");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user != null) {
            for (Role role : user.getRoleList()) {
                // 基于Role的权限信息
                info.addRole(role.getName());
                // 基于Permission的权限信息
                info.addStringPermissions(role.getPermissionList());
            }
        }
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
        matcher.setHashIterations(AccountService.HASH_INTERATIONS);

        setCredentialsMatcher(matcher);
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Integer uid;
        public String account;
        public String nickname;

        public ShiroUser(Integer uid, String account, String nickname) {
            this.uid = uid;
            this.account = account;
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public String getAccount() {
            return account;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return account;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(account);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (account == null) {
                if (other.account != null) {
                    return false;
                }
            } else if (!account.equals(other.account)) {
                return false;
            }
            return true;
        }
    }
}
