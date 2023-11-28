package com.placideh.usermgtapi.controller;

import com.placideh.usermgtapi.entity.Task;
import com.placideh.usermgtapi.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping( value = "/create/{projectId}")
    public ResponseEntity<Map<String, Task>> createTask(@PathVariable Integer projectId,@Valid @RequestBody Task task) {
        Map<String ,Task> message=new HashMap<>();
        Task newTask = taskService.createTask(projectId,task);
        message.put("success",newTask);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Task>> getTaskById(@PathVariable Integer id){
        Map<String,Task> message=new HashMap<>();
        Task task= taskService.getTaskById(id);
        message.put("success",task);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Task>>> getTasks(){
        Map<String,List<Task>> message=new HashMap<>();
        List<Task> tasks= taskService.getTasks();
        message.put("success",tasks);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity updateTaskById(@PathVariable Integer id,@Valid @RequestBody Task task){
        taskService.updateTask(id,task);
        return new ResponseEntity<>( HttpStatus. NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> removeTaskById(@PathVariable Integer id){
        Task deletedTask =taskService.deleteTaskById(id);
        return new ResponseEntity<>(deletedTask,HttpStatus.OK);
    }

}
