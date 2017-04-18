package com.mws.model.repositories;

import com.mws.model.AppUpgrade;
import com.mws.model.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Created by ranfi on 2/29/16.
 */
public interface AppUpgradeDao extends BaseDao<AppUpgrade, Integer> {


    @Query("from AppUpgrade a ")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<AppUpgrade> getLatestAppUpgrade(Pageable request);
}
