package com.placideh.usermgtapi.repository;


import com.placideh.usermgtapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Integer> {
    @Override
    Optional<Project> findById(Integer integer);


}
