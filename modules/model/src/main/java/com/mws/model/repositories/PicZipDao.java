package com.mws.model.repositories;

import com.mws.model.BaseDao;
import com.mws.model.PicZip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * 
 */
public interface PicZipDao extends BaseDao<PicZip, Integer> {

    @Query("from PicZip a ")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<PicZip> getLatestPicZip(Pageable request);

}
