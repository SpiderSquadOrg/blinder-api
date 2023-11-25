package com.blinder.api.user.service;

import com.blinder.api.user.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    User getUserById(String id);

    User addUser(User user);

    void deleteUserById(String userId);

    User banUserById(String userId);

    User unbanUserById(String userId);

    User blockUserById(String userId);

    User unblockUserById(String userId);

    Optional<User> findByUsername(String username);

    Page<User> getUsers(Integer page, Integer size);
}
