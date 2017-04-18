package com.mws.model.repositories;

import com.mws.model.BaseDao;
import com.mws.model.sys.Param;

public interface SystemParamDao extends BaseDao<Param, Integer> {

	Param findSysParamByKey(String key);
}
