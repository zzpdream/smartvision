package com.mws.web.service;

import com.mws.model.BaseDao;
import com.mws.model.PicZip;
import com.mws.model.repositories.PicZipDao;
import com.mws.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zlj on 2/29/16.
 */

@Service
public class PicZipService extends BaseService<PicZip, Integer>{


    private PicZipDao picZipDao;
    
    @Resource
    private CommonService commonService;

    @Resource
	@Override
	public void setBaseDao(BaseDao<PicZip, Integer> baseDao) {
		this.baseDao = baseDao;
		picZipDao =( PicZipDao)baseDao;
	}
    
    /**
	 * 查询picZip信息
	 * 
	 * @param id
	 *            picZip编号
	 * @return picZip信息
	 */
	@Transactional(readOnly = true)
	public PicZip findPicZip(Integer id) {
		if (id == null) {
			return null;
		}
		return picZipDao.findOne(id);
	}

	/**
	 * 获取最新的一条图片资源
	 *
	 * @return
	 */
	public PicZip getLatestPicZip() {
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		PageRequest request = new PageRequest(0, 1, sort);
		Page<PicZip> page = picZipDao.getLatestPicZip(request);
		List<PicZip> list = page.getContent();
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 保存picZip
	 * 
	 *  picZip
	 * @return picZip
	 */
	@Override
	@Transactional
	public PicZip save(PicZip picZip) {
		//从数据库总查询picZip信息
		PicZip entity = findPicZip(picZip.getId());
		if (entity == null) {
			// 新建picZip
			entity = picZip;
			entity.setCreateTime(commonService.getCurrentTime());
		} else {
			// 保存picZip
			Timestamp createTime = entity.getCreateTime();
			entity = picZip;
			entity.setCreateTime(createTime);
		}

		return picZipDao.save(entity);
	}

	/**
	 * 删除picZip
	 * 
	 * @param ids
	 *            编号数组
	 */
	@Override
	@Transactional
	public void delete(Integer[] ids) {
		PicZip picZip;
		for (Integer id : ids) {
			picZip = findPicZip(id);
			if (picZip == null) {
				continue;
			}
			picZipDao.delete(picZip);
		}
	}
	
}
