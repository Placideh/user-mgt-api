package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task createTask(Integer projectId, Task task);

    Task getTaskById(Integer taskId);
    List<Task> getTasks();
    Task updateTask(Integer taskId,Task task);
    Task deleteTaskById(Integer taskId);

}
