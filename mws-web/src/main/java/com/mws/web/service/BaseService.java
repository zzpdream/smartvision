package com.mws.web.service;

import com.mws.model.BaseDao;
import com.mws.web.common.bo.SystemGlobal;
import com.mws.web.common.persistence.DynamicSpecifications;
import com.mws.web.common.persistence.SearchFilter;
import com.mws.web.common.vo.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * 抽象公用实现类，任何service都继承它,提供一些公用的操作
 * 
 * @author ranfi
 * 
 */
public abstract class BaseService<E, PK extends Serializable> {

	protected final Logger logger = LoggerFactory.getLogger(BaseService.class);

	protected BaseDao<E, PK> baseDao;

	public abstract void setBaseDao(BaseDao<E, PK> baseDao);

	/**
	 * 根据页码和条件查询分页数据
	 *
	 * @param pageNum 页码
	 * @param params 条件
	 * @return 分页数据
	 */
	@Transactional(readOnly = true)
	public Page<E> list(int pageNum, Map<String, Object> params) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		PageRequest pr = new PageRequest(pageNum - 1, SystemGlobal.PAGE_SIZE, sort);
		Map<String, SearchFilter> filters = SearchFilter.parse(params);
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				(Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		return baseDao.findAll(spec, pr);
	}

	/**
	 * 根据分页信息和查询条件查询
	 *
	 * @param pagination 分页信息
	 * @param params 查询条件
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public Page<E> list(Pagination pagination, Map<String, Object> params) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		return list(pagination, params, sort);
	}

	/**
	 * 根绝分页信息、查询条件和排序查询
	 *
	 * @param pagination 分页信息
	 * @param params 查询条件
	 * @param sort 排序
	 * @return 分页信息
	 */
	@Transactional(readOnly = true)
	public Page<E> list(Pagination pagination, Map<String, Object> params, Sort sort) {
		PageRequest pr = new PageRequest(pagination.getPage() - 1, pagination.getPageSize(), sort);
		Map<String, SearchFilter> filters = SearchFilter.parse(params);
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				(Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		Page<E> ret = baseDao.findAll(spec, pr);
		return ret;
	}

	/**
	 * 查询分页数据
	 *
	 * @param pageNum 页码
	 * @param params 条件
	 * @param pageSize 每页条数
	 * @param sort 排序
	 * @return 分页数据
	 */
	@Transactional(readOnly = true)
	public Page<E> list(int pageNum, Map<String, Object> params, int pageSize, Sort sort) {
		PageRequest pr = new PageRequest(pageNum - 1, pageSize, sort);
		Map<String, SearchFilter> filters = SearchFilter.parse(params);
		Specification<E> spec = DynamicSpecifications.bySearchFilter(filters.values(),
				(Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		return baseDao.findAll(spec, pr);
	}

	/**
	 * 查询实体对象
	 *
	 * @param id 编号
	 * @return 实体对象
	 */
	@Transactional(readOnly = true)
	public E get(PK id) {
		if (id != null) {
			return baseDao.findOne(id);
		}
		return null;
	}

	/**
	 * 保存实体对象
	 *
	 * @param e 实体对象
	 * @return 保存后的实体对象
	 */
	@Transactional
	public E save(E e) {
		return baseDao.save(e);
	}

	/**
	 * 删除实体对象
	 *
	 * @param id 编号
	 */
	@Transactional
	public void delete(PK id) {
		if (id != null) {
			baseDao.delete(id);
		}
	}

	/**
	 * 删除实体对象
	 *
	 * @param ids 编号数组
	 */
	@Transactional
	public void delete(PK[] ids) {
		for(PK id : ids) {
			delete(id);
		}
	}
}
