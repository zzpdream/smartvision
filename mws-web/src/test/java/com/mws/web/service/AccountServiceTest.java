/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.mws.web.service;

import com.mws.core.spring.Profiles;
import com.mws.model.sys.User;
import com.mws.web.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-web.xml"})
public class AccountServiceTest {

	static {
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
	}

	// @InjectMocks
	@Resource
	private AccountService accountService;

	// @Mock
	@Resource
	private UserRepository mockUserDao;


	@Before
	public void setUp() {
		// MockitoAnnotations.initMocks(this);
		//ShiroTestUtils.mockSubject(new ShiroUser(1, "foo", "Foo"));
	}

	@After
	public void tearDown() {
		//ShiroTestUtils.clearSubject();
	}



	@Test
	public void saveUser() {

		User user = new User();
		user.setAccount("admin");
		user.setNickname("admin");
		user.setPhone("15501675033");
		user.setEmail("admin@qq.com");
		user.setCreateTime(new Timestamp(System.nanoTime()));
		user.setPassword("123456");
		try {
			// 正常保存用户.
			accountService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Mockito.verify(mockUserDao, Mockito.never()).delete(1L);
	}
	
}
