package com.placideh.usermgtapi.service.impl;

import com.placideh.usermgtapi.Dto.TaskDto;
import com.placideh.usermgtapi.entity.Project;
import com.placideh.usermgtapi.entity.Task;
import com.placideh.usermgtapi.entity.User;
import com.placideh.usermgtapi.exception.CustomInputException;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.repository.ProjectRepo;
import com.placideh.usermgtapi.repository.TaskRepo;
import com.placideh.usermgtapi.repository.UserRepo;
import com.placideh.usermgtapi.service.TaskService;
import com.placideh.usermgtapi.utils.CustomDateConverter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    public TaskServiceImpl(TaskRepo taskRepo, ProjectRepo projectRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Task createTask(Integer projectId,Integer userId, TaskDto task) {

        Task convertedTask= CustomDateConverter.convertTaskDtoToSchedule(task);
        if ((convertedTask.getStartDate().isAfter(convertedTask.getEndDate())))
            throw new CustomInputException("You can only create a task where start Date which is not after ending Date ");

        if ((convertedTask.getStartDate().isAfter(convertedTask.getEndDate())) && (convertedTask.getStartTime().isAfter(convertedTask.getEndTime())))
            throw new CustomInputException("You can only create a task where start time which is not after ending time ");

        if ((convertedTask.getStartDate().equals(convertedTask.getEndDate())) && convertedTask.getStartTime().isAfter(convertedTask.getEndTime()))
            throw new CustomInputException("You can only Assign task only when start time  is not after ending time ");

        Project existingProject = projectRepo.findById(projectId).orElseThrow(()->new NotFoundException("Project with ID:"+projectId+"Not found"));

        User existingUser = userRepo.findById(userId).orElseThrow(()->new NotFoundException("User with ID:"+userId+"Not found"));
        Task newTask =Task.builder()
                .project(existingProject)
                .user(existingUser)
                .attachments(task.getAttachments())
                .endDate(convertedTask.getEndDate())
                .startDate(convertedTask.getStartDate())
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
