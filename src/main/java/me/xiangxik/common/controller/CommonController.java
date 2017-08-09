package me.xiangxik.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.xiangxik.support.web.BaseController;

@Controller
public class CommonController extends BaseController {

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		return "/index";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detailPage() {
		return "/detail";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "/login";
	}

}
