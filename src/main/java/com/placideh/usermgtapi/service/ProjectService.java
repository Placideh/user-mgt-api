package com.placideh.usermgtapi.service;

import com.placideh.usermgtapi.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProjectService {
    Project createProject(Project project);

    Project getProjectById(Integer projectId);
    List<Project> getProjects();
    Project updateProject(Integer projectId,Project project);
    Project deleteProjectById(Integer taskId);
}
