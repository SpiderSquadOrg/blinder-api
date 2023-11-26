package com.blinder.api.user.service;

import com.blinder.api.user.model.Role;
import org.springframework.data.domain.Page;

public interface RoleService {
    Role addRole(Role role);

    Page<Role> getRoles(Integer page, Integer size);

    Role getRoleById(String roleId);

    Role updateRole(String roleId, Role role);

    void deleteRole(String roleId);
}
