package com.mws.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.sql.Timestamp;

@Service
public class CommonService {

	@PersistenceContext
	private EntityManager em;

	// 查询当前时间
	public Timestamp getCurrentTime() {
		Query query = em.createNativeQuery("select now() from dual");
		Timestamp currTime = (Timestamp) query.getSingleResult();
		return currTime;
	}

	// 查询当前日期
	public Date getCurrentDate() {
		Query query = em.createNativeQuery("select CURDATE() from dual");
		Date currDate = (Date) query.getSingleResult();
		return currDate;
	}

	//查询N天后的时间
	public Timestamp getNextDateTime(Integer date) {
		Query query = em.createNativeQuery("select DATE_ADD(NOW(),INTERVAL "+date+" DAY)  from dual");
		Timestamp NextDateTime = (Timestamp) query.getSingleResult();
		return NextDateTime;
	}
}
