package me.xiangxik.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castle.repo.jpa.EntityService;

import me.xiangxik.user.entity.User;
import me.xiangxik.user.repo.UserRepository;

@Service
public class UserService extends EntityService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> findTop5Post() {
		return userRepository.findTop5ByOrderByCreatedDateDesc();
	}
}
