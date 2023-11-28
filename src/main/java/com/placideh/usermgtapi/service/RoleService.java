package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role createRole(String roleName);

    Role getRoleById(Integer roleId);
    Role getRoleByName(String roleName);
}
