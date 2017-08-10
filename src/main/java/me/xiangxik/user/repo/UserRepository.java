package me.xiangxik.user.repo;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.castle.repo.jpa.EntityRepository;

import me.xiangxik.user.entity.QUser;
import me.xiangxik.user.entity.User;

public interface UserRepository extends EntityRepository<User, Long>, QuerydslBinderCustomizer<QUser> {

	User findByUsername(String username);

	List<User> findTop5ByOrderByCreatedDateDesc();

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(root.username, root.name, root.email, root.mobile).first((path, value) -> path.contains(value));
		bindings.excluding(root.password);
	}
}
