package com.placideh.usermgtapi.service.impl;

import com.placideh.usermgtapi.entity.Role;
import com.placideh.usermgtapi.exception.AlreadyExistException;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.repository.RoleRepo;
import com.placideh.usermgtapi.service.RoleService;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role createRole(String roleName) {
        boolean isExist=roleRepo.findByRoleNameIgnoreCase(roleName).isPresent();
        if (isExist) throw new AlreadyExistException("role already exist");
        Role role=Role.builder().roleName(roleName).build();
        return roleRepo.save(role);
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepo.findById(roleId)
                .orElseThrow(()-> new NotFoundException("roleId: "+roleId+" not found"));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepo.findByRoleNameIgnoreCase(roleName)
                .orElseThrow(()-> new NotFoundException("roleName: "+roleName+" not found "));
    }
}
