package com.placideh.usermgtapi.service.impl;

import com.placideh.usermgtapi.entity.Project;
import com.placideh.usermgtapi.entity.Task;
import com.placideh.usermgtapi.exception.NotFoundException;
import com.placideh.usermgtapi.repository.ProjectRepo;
import com.placideh.usermgtapi.service.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepo projectRepo;

    public ProjectServiceImpl(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }


    @Override
    public Project createProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return projectRepo.findById(projectId).orElseThrow(()-> new NotFoundException("Project with ID:"+projectId+" not found"));
    }

    @Override
    public List<Project> getProjects() {
        return projectRepo.findAll();
    }

    @Override
    public Project updateProject(Integer projectId ,Project project) {
        projectRepo.findById(projectId).orElseThrow(()-> new NotFoundException("Project with ID:"+projectId+" not found"));
         return projectRepo.save(project);
    }

    @Override
    public Project deleteProjectById(Integer projectId) {
         Project deletedProject=projectRepo.findById(projectId).orElseThrow(()-> new NotFoundException("Project with ID:"+projectId+" not found"));
         projectRepo.delete(deletedProject);
         return deletedProject;
    }
}
