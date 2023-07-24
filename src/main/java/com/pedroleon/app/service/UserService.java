package com.pedroleon.app.service;

import com.pedroleon.app.domain.UserEntity;
import com.pedroleon.app.web.rest.dto.UserDTO;

public interface UserService {
	UserEntity save(UserEntity user);

	UserEntity findByUsername(String string);

	UserEntity registerUser(UserDTO user);
}
