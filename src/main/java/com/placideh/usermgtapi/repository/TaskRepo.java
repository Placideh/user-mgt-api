package com.placideh.usermgtapi.repository;

import com.placideh.usermgtapi.entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends PagingAndSortingRepository<Task,Integer> {
    @Override
    Optional<Task> findById(Integer integer);
}
