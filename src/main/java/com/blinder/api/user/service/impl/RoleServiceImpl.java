package com.blinder.api.user.service.impl;

import com.blinder.api.user.model.Role;
import com.blinder.api.user.repository.RoleRepository;
import com.blinder.api.user.rules.RoleBusinessRules;
import com.blinder.api.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleBusinessRules roleBusinessRules;

    @Override
    public Role addRole(Role role) {
        this.roleBusinessRules.checkIfNameExists(role.getName());
        return this.roleRepository.save(role);
    }

    @Override
    public Page<Role> getRoles(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.roleRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Role getRoleById(String roleId) {
        return this.roleRepository.findById(roleId).orElseThrow();
    }

    @Override
    public Role updateRole(String roleId, Role role) {
        Role roleToUpdate = this.roleRepository.findById(roleId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(role);

        BeanUtils.copyProperties(role, roleToUpdate, nullPropertyNames.toArray(new String[0]));

        this.roleRepository.save(roleToUpdate);
        return roleToUpdate;
    }

    @Override
    public void deleteRole(String roleId) {
        this.roleRepository.deleteById(roleId);
    }
}
