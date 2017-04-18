package com.mws.web.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mws.model.AppUpgrade;
import com.mws.model.BaseDao;
import com.mws.model.repositories.AppUpgradeDao;
import com.mws.service.CommonService;

/**
 * Created by zlj on 2/29/16.
 */

@Service
public class AppUpgradeService extends BaseService<AppUpgrade, Integer>{


    private AppUpgradeDao appUpgradeDao;
    
    @Resource
    private CommonService commonService;

    @Resource
	@Override
	public void setBaseDao(BaseDao<AppUpgrade, Integer> baseDao) {
		this.baseDao = baseDao;
		appUpgradeDao =( AppUpgradeDao)baseDao;
	}

    /**
     * 获取最新的一条APP升级记录
     *
     * @return
     */
    public AppUpgrade getLatestAppUpgrade() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest request = new PageRequest(0, 1, sort);
        Page<AppUpgrade> page = appUpgradeDao.getLatestAppUpgrade(request);
        List<AppUpgrade> list = page.getContent();
        return list.size() > 0 ? list.get(0) : null;
    }

    
    /**
	 * 查询APK信息
	 * 
	 * @param id
	 *            APK编号
	 * @return APK信息
	 */
	@Transactional(readOnly = true)
	public AppUpgrade findAppUpgrade(Integer id) {
		if (id == null) {
			return null;
		}
		return appUpgradeDao.findOne(id);
	}

	/**
	 * 保存APK
	 * 
	 * @param appUpgrade
	 *            APK
	 * @return APK
	 */
	@Override
	@Transactional
	public AppUpgrade save(AppUpgrade appUpgrade) {
		// 从数据库总查询APK信息
		AppUpgrade entity = findAppUpgrade(appUpgrade.getId());
		if (entity == null) {
			// 新建APK
			entity = appUpgrade;
			entity.setCreateTime(commonService.getCurrentTime());
		} else {
			// 保存APK
			Timestamp createTime = entity.getCreateTime();
			entity = appUpgrade;
			entity.setCreateTime(createTime);
		}

		return appUpgradeDao.save(entity);
	}

	/**
	 * 删除APK
	 * 
	 * @param ids
	 *            编号数组
	 */
	@Override
	@Transactional
	public void delete(Integer[] ids) {
		AppUpgrade appUpgrade;
		for (Integer id : ids) {
			appUpgrade = findAppUpgrade(id);
			if (appUpgrade == null) {
				continue;
			}
			appUpgradeDao.delete(appUpgrade);
		}
	}
	
}
