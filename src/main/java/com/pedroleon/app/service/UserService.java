package com.pedroleon.app.service;

import com.pedroleon.app.domain.UserEntity;
import java.util.Optional;

public interface UserService {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findByUsername(String string);
}
