package com.placideh.usermgtapi.controller;

import com.placideh.usermgtapi.entity.Project;
import com.placideh.usermgtapi.service.ProjectService;
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
@RequestMapping("/api/v1/projects")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping( value = "/create")
    public ResponseEntity<Map<String, Project>> createTask(@Valid @RequestBody Project project) {
        Map<String ,Project> message=new HashMap<>();
        Project newTask = projectService.createProject(project);
        message.put("success",newTask);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Project>> getProjectById(@PathVariable Integer id){
        Map<String,Project> message=new HashMap<>();
        Project existingProject= projectService.getProjectById(id);
        message.put("success",existingProject);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Project>>> getTasks(){
        Map<String,List<Project>> message=new HashMap<>();
        List<Project> projects= projectService.getProjects();
        message.put("success",projects);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity updateProjectById(@PathVariable Integer id,@Valid @RequestBody Project project){
        projectService.updateProject(id,project);
        return new ResponseEntity<>( HttpStatus. NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProjectById(@PathVariable Integer id){
        Project deletedTask =projectService.deleteProjectById(id);
        return new ResponseEntity<>(deletedTask,HttpStatus.OK);
    }
}
