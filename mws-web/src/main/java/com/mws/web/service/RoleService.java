package com.mws.web.service;

import com.mws.model.BaseDao;
import com.mws.model.sys.Menu;
import com.mws.model.sys.Role;
import com.mws.service.CommonService;
import com.mws.web.common.exception.WebRequestException;
import com.mws.web.repository.MenuRepository;
import com.mws.web.repository.RoleRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Service - 角色
 */
@Service
public class RoleService extends BaseService<Role, Integer> {

	private final Logger logger = LoggerFactory.getLogger(RoleService.class);

	private RoleRepository roleDao;

    @Resource
    private CommonService commonService;

    @Resource
    private AccountService accountService;

    @Resource
    private MenuRepository menuDao;

    @Override
    @Resource
    public void setBaseDao(BaseDao<Role, Integer> baseDao) {
        this.baseDao = baseDao;
        roleDao = (RoleRepository) baseDao;
    }

    /**
     * 查询角色
     *
     * @param id 编号
     * @return 角色信息
     */
    @Transactional
	public Role findRole(Integer id) {
        if (id != null && id > 0) {
            return roleDao.findOne(id);
        }
        return null;
	}

    /**
     * 保存角色
     *
     * @param role 角色信息
     * @return 角色
     */
    @Override
    @Transactional
    public Role save(Role role) {
        Role entity = null;

        // 查询角色信息
        if (role.getId() != null && role.getId() > 0) {
            entity = roleDao.findOne(role.getId());
        }

        // 判断数据库中的角色是否存在
        if (entity == null) {
            // 创建新角色
            entity = role;
            entity.setCreateTime(commonService.getCurrentTime());
            entity.setUser(accountService.getCurrentUser());
        } else {
            // 更新角色信息
            entity.setName(role.getName());
            entity.setMenus(role.getMenus());
            entity.setPermissions(role.getPermissions());
        }

        // 遍历添加所有父菜单
        List<Menu> rootMenus = new ArrayList<Menu>();
        for (Menu menu : entity.getMenus()) {
            menu = menuDao.findOne(menu.getId());
            if (menu.getParentMenu() != null && !rootMenus.contains(menu.getParentMenu())) {
                rootMenus.add(menu.getParentMenu());
            }
        }
        entity.getMenus().addAll(rootMenus);


        return super.save(entity);
    }

    /**
     * 删除角色
     *
     * @param id 编号
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        if (id != null && id > 0) {
            // 查询角色信息
            Role role = findRole(id);

            if (role != null) {
                // 判断角色是否关联了用户
                if (CollectionUtils.isNotEmpty(role.getUsers())) {
                    throw new WebRequestException("删除失败！请先取消用户对 ["+ role.getName() +"] 角色的关联");
                }

                // 删除角色
                roleDao.delete(role);
            }
        }
    }

    /**
     * 删除角色
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
     * 查询所有角色信息
     *
     * @return 角色信息
     */
    @Transactional(readOnly = true)
    public List<Role> findRoleList() {
        return roleDao.findRoleList();
    }

    private boolean isContains(Menu menu, List<Menu> rootMenus) {
        for (Menu root : rootMenus) {
            if (root.getId() == menu.getId()) {
                return true;
            }
        }
        return false;
    }
}
