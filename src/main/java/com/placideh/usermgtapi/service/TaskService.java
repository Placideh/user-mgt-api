package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.Dto.TaskDto;
import com.placideh.usermgtapi.entity.Task;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public interface TaskService {
    Task createTask(Integer projectId,Integer userId, TaskDto task);

    Task getTaskById(Integer taskId);
     Iterator<Task> getTasks();
    Task updateTask(Integer taskId,Task task);
    Task deleteTaskById(Integer taskId);
    List<Task> getTaskByPagination(int pageNo, int pageSize);

}
