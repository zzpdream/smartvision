package com.mws.model;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Dao - Base
 *
 * @date 2015-05-22
 */
public interface BaseDao<E, PK extends Serializable> extends CrudRepository<E, PK>, PagingAndSortingRepository<E, PK>, JpaSpecificationExecutor<E> {


}
