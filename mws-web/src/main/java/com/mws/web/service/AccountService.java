/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.mws.web.service;

import com.mws.core.utils.Digests;
import com.mws.core.utils.EncodeUtils;
import com.mws.model.BaseDao;
import com.mws.model.sys.User;
import com.mws.service.CommonService;
import com.mws.web.common.bo.Constant;
import com.mws.web.context.ParameterCache;
import com.mws.web.repository.RoleRepository;
import com.mws.web.repository.UserRepository;
import com.mws.web.shiro.ShiroDbRealm.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Service - 系统用户
 */
@Service
public class AccountService extends BaseService<User, Integer> {

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    private static final int SALT_SIZE = 8;

    @Resource
    private CommonService commonService;

    private UserRepository userDao;

    @Resource
    private RoleRepository roleDao;

    @Override
    @Resource
    public void setBaseDao(BaseDao<User, Integer> baseDao) {
        this.baseDao = baseDao;
        userDao = (UserRepository) baseDao;
    }

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    @Transactional
    public User save(User user) {

        // 从数据库中查询用户信息
        User entity = getUser(user.getId());

        if (entity == null) {
            // 创建新用户
            entity = user;
            entity.setCreateTime(commonService.getCurrentTime());
            entity.setDeleted(false);
            entity.setStatus((byte) Constant.Status.Enable.value);

            // 密码加密
            entryptPassword(entity);
        } else {
            // 更新用户信息
            entity.setAccount(user.getAccount());
            entity.setFullname(user.getFullname());
            entity.setNickname(user.getNickname());
            entity.setStatus(user.getStatus());
            entity.setEmail(user.getEmail());
            entity.setGender(user.getGender());
            entity.setPhone(user.getPhone());
            entity.setRoleList(user.getRoleList());

            // 判断密码是否为空，不为空则修改当密码，为空则不修改
            if (StringUtils.isNotBlank(user.getPassword())) {
                entity.setPassword(user.getPassword());
                entryptPassword(entity);
            }
        }

        return userDao.save(entity);
    }

    /**
     * 查询用户信息
     *
     * @param id 编号
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        if (id != null) {
            return userDao.findOne(id);
        }
        return null;
    }

    /**
     * 删除系统用户
     *
     * @param ids 编号数组
     */
    @Override
    @Transactional
    public void delete(Integer[] ids) {
        if (ids != null && ids.length > 0) {
            for (Integer id : ids) {
                delete(id);
            }
        }
    }

    /**
     * 删除系统用户
     *
     * @param id 编号
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        // 查询系统用户信息
        User user = getUser(id);

        if (user != null) {
            // 伪删除用户
            user.setDeleted(true);
            userDao.save(user);
        }
    }

    /**
     * 更新用户状态
     *
     * @param ids    编号
     * @param status 状态
     */
    @Transactional
    public void updateStatus(Integer[] ids, Constant.Status status) {
        if (ids != null && ids.length > 0) {
            for (Integer id : ids) {
                updateStatus(id, status);
            }
        }
    }

    /**
     * 更新用户状态
     *
     * @param id     编号
     * @param status 状态
     */
    @Transactional
    public void updateStatus(Integer id, Constant.Status status) {
        // 查询用户信息
        User user = getUser(id);

        if (user != null) {
            // 把用户状态设置正常
            user.setStatus((byte) status.value);
            userDao.save(user);
        }
    }

    /**
     * 按登录帐号查询用户信息
     *
     * @param account 帐号
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public User findUserByAccount(String account) {
        return userDao.findByAccount(account);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     *
     * @param user 用户信息
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(EncodeUtils.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(EncodeUtils.encodeHex(hashPassword));
    }

    /**
     * 获取加密密码
     *
     * @param salt
     * @param password
     * @return
     */
    public String getEntryptPassword(String salt, String password) {
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt.getBytes(), HASH_INTERATIONS);
        return EncodeUtils.encodeHex(hashPassword);
    }

    /**
     * 取出Shiro中的当前用户登陆帐号
     */
    public String getCurrentAccount() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.account;
    }

    /**
     * 取出Shiro中的当前登陆用户
     *w
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return findUserByAccount(getCurrentAccount());
    }

    /**
     * 判断是否超级管理员.
     */
    @Deprecated
    public boolean isSupervisor(Integer uid) {
        return ((uid != null) && (uid == 1L));
    }

    /**
     * 判断是否超级管理员.
     */
    public boolean isSupervisor(String account) {
        if (StringUtils.isNotBlank(account)) {
            return account.equalsIgnoreCase(ParameterCache.getSystemProp("system.admin.id"));
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setAccount("ranfi");
        user.setPassword("987654321");
        new AccountService().entryptPassword(user);
    }

}
