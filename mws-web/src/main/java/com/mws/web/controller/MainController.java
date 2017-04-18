package com.mws.web.controller;

import com.mws.web.context.ParameterCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/main")
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
		return "main";
	}

}
