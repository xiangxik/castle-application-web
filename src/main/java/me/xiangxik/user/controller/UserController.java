package me.xiangxik.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.castle.core.CastleConstants;
import com.castle.repo.domain.Result;
import com.castle.security.CurrentUser;
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

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(@CurrentUser User user, Model model) {
		model.addAttribute("user", user);
		return getBaseTemplatePath() + "/profile";
	}

	@RequestMapping(value = "/profile_edit", method = RequestMethod.GET)
	public String profileEdit(@CurrentUser User user, Model model) {
		model.addAttribute("entity", user);
		return getBaseTemplatePath() + "/profile_edit";
	}

	@RequestMapping(value = "/profile_save", method = RequestMethod.POST, params = { "!type", "!deleted", "!locked", "!disabled" })
	@ResponseBody
	public Result doProfileSave(@ModelAttribute @Valid User entity, BindingResult bindingResult) {
		onValidate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			return Result.validateError().error(bindingResult.getAllErrors());
		}

		onBeforeSave(entity);
		getService().save(entity);
		onAfterSave(entity);

		return Result.success();
	}

}
