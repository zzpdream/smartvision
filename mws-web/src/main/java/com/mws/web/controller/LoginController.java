package com.mws.web.controller;

import com.mws.web.context.ParameterCache;
import com.mws.web.service.AccountService;
import com.mws.web.shiro.ShiroDbRealm;
import com.mws.web.web.WebConstants;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author ranfi
 */
@Controller
public class LoginController {
	

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user != null) {
			return "redirect:/main";
		}
		model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
//		SecurityUtils.getSubject().getSession().removeAttribute(WebConstants.LOGIN_USER_KEY);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
