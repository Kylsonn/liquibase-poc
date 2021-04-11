package com.ultracar.ultracarweb.service;

import com.ultracar.ultracarweb.model.User;
import com.ultracar.ultracarweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	public List<User> getUsers(){
		return userRepository.findAll();
	}
}
