package com.placideh.usermgtapi.service.impl;

import com.placideh.usermgtapi.entity.Project;
import com.placideh.usermgtapi.entity.Status;
import com.placideh.usermgtapi.entity.Task;
import com.placideh.usermgtapi.entity.User;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.repository.ProjectRepo;
import com.placideh.usermgtapi.repository.TaskRepo;
import com.placideh.usermgtapi.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;

    public TaskServiceImpl(TaskRepo taskRepo, ProjectRepo projectRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    public Task createTask(Integer projectId,Task task) {

        Project existingProject = projectRepo.findById(projectId).orElseThrow(()->new NotFoundException("Project with ID:"+projectId+"Not found"));
        Task newTask =Task.builder()
                .project(existingProject)
                .attachments(task.getAttachments())
                .endDate(task.getEndDate())
                .startDate(task.getStartDate())
                .title(task.getTitle())
                .priority(task.getPriority())
                .build();

        return taskRepo.save(newTask);
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return taskRepo.findById(taskId).orElseThrow(()-> new NotFoundException("Task with ID:"+taskId+" not found"));
    }

    @Override
    public List<Task> getTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Task updateTask(Integer taskId,Task task) {
        taskRepo.findById(taskId).orElseThrow(()-> new NotFoundException("Task with ID:"+taskId+" not found"));

        return taskRepo.save(task);
    }

    @Override
    public Task deleteTaskById(Integer taskId) {
        Task existingTask=taskRepo.findById(taskId).orElseThrow(()-> new NotFoundException("Task with ID:"+taskId+" not found"));
        taskRepo.delete(existingTask);

        return existingTask;


    }
}
