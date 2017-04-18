package com.mws.web.service;

import com.mws.model.BaseDao;
import com.mws.model.sys.Permission;
import com.mws.service.CommonService;
import com.mws.web.common.exception.WebRequestException;
import com.mws.web.repository.PermissionRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Service - 权限点
 *
 * @author xingkong1221
 * @date 2015-11-06
 */
@Service
public class PermissionService extends BaseService<Permission, Integer> {

    private PermissionRepository permissionDao;

    @Resource
    private CommonService commonService;

    @Override
    @Resource
    public void setBaseDao(BaseDao<Permission, Integer> baseDao) {
        this.baseDao = baseDao;
        permissionDao = (PermissionRepository) baseDao;
    }

    /**
     * 保存权限点
     *
     * @param permission 权限点信息
     * @return 权限点
     */
    @Override
    @Transactional
    public Permission save(Permission permission) {
        Permission entity = null;

        // 从数据库中查询权限点信息
        if (permission.getId() != null && permission.getId() > 0) {
            entity = get(permission.getId());
        }

        // 判断数据库中权限点信息是否存在
        if (entity == null) {
            // 创建新的权限点信息
            entity = permission;
            entity.setCreateTime(commonService.getCurrentTime());
        } else {
            // 更新权限点信息
            entity.setName(permission.getName());
            entity.setPermission(permission.getPermission());
            entity.setMenu(permission.getMenu());
        }

        // 保存权限点信息
        return super.save(entity);
    }

    /**
     * 删除权限点
     *
     * @param id 编号
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        if (id != null && id > 0) {
            // 查询权限点信息
            Permission permission = permissionDao.findOne(id);

            // 检查权限点是否已经被角色关联
            if (CollectionUtils.isNotEmpty(permission.getRoles())) {
                throw new WebRequestException("删除失败！请先取消权限点 ["+ permission.getName() +"] 关联的角色");
            }

            permissionDao.delete(permission);
        }
    }

    /**
     * 删除权限点
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
}
