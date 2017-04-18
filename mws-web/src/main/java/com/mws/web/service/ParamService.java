package com.mws.web.service;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mws.model.BaseDao;
import com.mws.model.repositories.SystemParamDao;
import com.mws.model.sys.Param;
import com.mws.service.CommonService;

/**
 * Service - 系统参数
 */
@Service
public class ParamService extends BaseService<Param, Integer> {

	private SystemParamDao systemParamDao;

	@Resource
	private CommonService commonService;

	@Resource
	@Override
	public void setBaseDao(BaseDao<Param, Integer> baseDao) {
		this.baseDao = baseDao;
		systemParamDao =( SystemParamDao)baseDao;
	}
	/**
	 * 查询系统参数信息
	 * 
	 * @param id
	 *            系统参数编号
	 * @return 系统参数信息
	 */
	@Transactional(readOnly = true)
	public Param findParam(Integer id) {
		if (id == null) {
			return null;
		}
		return systemParamDao.findOne(id);
	}

	/**
	 * 保存系统参数
	 * 
	 * @param scenic
	 *            系统参数
	 * @return 系统参数
	 */
	@Override
	@Transactional
	public Param save(Param param) {
		// 从数据库总查询系统参数信息
		Param entity = findParam(param.getId());
		// 保存系统参数
		if(entity != null){
			param.setKey(entity.getKey());
		}
		return systemParamDao.save(param);
	}
	
}
