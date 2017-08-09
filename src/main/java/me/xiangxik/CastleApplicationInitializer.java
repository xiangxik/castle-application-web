package me.xiangxik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.castle.integration.core.ApplicationInitializer;

import me.xiangxik.user.entity.User;
import me.xiangxik.user.entity.User.Type;
import me.xiangxik.user.service.UserService;

@Component
@Order(0)
public class CastleApplicationInitializer extends ApplicationInitializer {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onRootContextRefreshed() {
		super.onRootContextRefreshed();

		if (userService.count() == 0) {
			User superAdmin = userService.initDomain();
			superAdmin.setType(Type.superadmin);
			superAdmin.setUsername("admin");
			superAdmin.setPassword(passwordEncoder.encode("asd123"));
			superAdmin.setName("管理员");
			userService.save(superAdmin);
		}

	}
}
