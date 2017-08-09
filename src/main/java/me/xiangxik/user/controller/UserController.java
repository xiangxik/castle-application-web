package me.xiangxik.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.castle.core.CastleConstants;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;

import me.xiangxik.support.web.EntityController;
import me.xiangxik.user.entity.User;

@Controller
@RequestMapping("/user")
public class UserController extends EntityController<User, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Page<User> doPage(Predicate predicate, Pageable pageable) {
		return super.doInternalPage(predicate, pageable);
	}

	@Override
	protected void onValidate(User entity, BindingResult bindingResult) {
		super.onValidate(entity, bindingResult);

		String newPassword = getRequest().getParameter("newPassword");
		if (Strings.isNullOrEmpty(newPassword)) {
			if (entity.isNew()) {
				bindingResult.addError(new FieldError("entity", "newPassword", "密码不能为空"));
			}
		} else {
			if (!CastleConstants.isPassword(newPassword)) {
				bindingResult.addError(new FieldError("entity", "newPassword", "密码不符合格式"));
			}
		}
	}

	@Override
	protected void onBeforeSave(User entity) {
		super.onBeforeSave(entity);

		String newPassword = getRequest().getParameter("newPassword");
		if (!Strings.isNullOrEmpty(newPassword)) {
			entity.setPassword(passwordEncoder.encode(newPassword));
		}
	}

}