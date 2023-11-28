package com.placideh.usermgtapi.repository;

import com.placideh.usermgtapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {
    @Override
    Optional<Task> findById(Integer integer);
}
