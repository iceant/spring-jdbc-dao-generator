package com.github.iceant.spring.jdbc.dao.generator.webapp.services;

import com.github.iceant.spring.jdbc.dao.generator.webapp.storage.bean.Project;
import com.github.iceant.spring.jdbc.dao.generator.webapp.storage.dao.ProjectDAO;
import com.github.iceant.spring.jdbc.dao.generator.webapp.utils.AppUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    private final ProjectDAO projectDAO;

    public BusinessService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Project createProject(String name, String folder) {
        Long id = AppUtil.id();
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setFolder(folder);
        return projectDAO.insert(project);
    }

    public Project updateProject(Project project){
        return projectDAO.update(project);
    }

    public List<Project> listProjects() {
        return projectDAO.listAll();
    }

    public Integer clearProjects() {
        return projectDAO.deleteAll();
    }

    public Project findProjectById(Long id){
        return projectDAO.findByPk(id);
    }
}
