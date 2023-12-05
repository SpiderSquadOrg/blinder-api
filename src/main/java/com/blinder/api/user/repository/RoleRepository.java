package com.blinder.api.user.repository;

import com.blinder.api.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
