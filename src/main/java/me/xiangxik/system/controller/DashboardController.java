package me.xiangxik.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@RequestMapping(method = RequestMethod.GET)
	public String show(Model model) {
		return "/dashboard";
	}

}
