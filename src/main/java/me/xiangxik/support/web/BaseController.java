package me.xiangxik.support.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.castle.integration.webapp.mvc.WebController;

import me.xiangxik.security.AuthenticationAuditorAware;
import me.xiangxik.user.entity.User;

public abstract class BaseController extends WebController {

	@Autowired
	private AuthenticationAuditorAware authenticationAuditorAware;

	@ModelAttribute("currentUser")
	public User getCurrentUser() {
		return authenticationAuditorAware.getCurrentAuditor();
	}
}
