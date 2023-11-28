package com.placideh.usermgtapi.repository;


import com.placideh.usermgtapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleNameIgnoreCase(String roleName);
}
