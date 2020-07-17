package com.github.iceant.spring.jdbc.dao.generator.webapp.controller.api;

import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGenerator;
import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGeneratorProperties;
import com.github.iceant.spring.jdbc.dao.generator.webapp.services.BusinessService;
import com.github.iceant.spring.jdbc.dao.generator.webapp.storage.bean.Project;
import com.github.iceant.spring.jdbc.dao.generator.webapp.utils.AppUtil;
import com.github.iceant.spring.jdbc.dao.generator.webapp.utils.SpringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/project"})
public class ProjectApiController {

    final BusinessService businessService;

    public ProjectApiController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @RequestMapping(path = {"/create"})
    public Object create() {
        return AppUtil.makeResponse(o -> {
            String name = SpringUtil.paramString("name");
            String folder = SpringUtil.paramString("folder");
            if (name == null || folder == null) return null;
            return businessService.createProject(name, folder);
        });
    }

    @RequestMapping(path = "/list")
    public Object list() {
        return AppUtil.makeResponse(o -> {
            return businessService.listProjects();
        });
    }

    @RequestMapping(path = {"/clear"})
    public Object clear(){
        return AppUtil.makeResponse(o->{
            return businessService.clearProjects();
        });
    }

    @RequestMapping(path = {"/find"})
    public Object findById(){
        return AppUtil.makeResponse(o->{
            Long id = SpringUtil.paramLong("id");
            if(id==null) return null;
            return businessService.findProjectById(id);
        });
    }

    @RequestMapping(path = {"/generate"})
    public Object generate(){
        return AppUtil.makeResponse(o->{
            Long id = SpringUtil.paramLong("id");
            String repositoryBeanPackage = SpringUtil.paramString("repositoryBeanPackage");
            String daoBeanPackage = SpringUtil.paramString("daoBeanPackage");
            String tableBeanPackage = SpringUtil.paramString("tableBeanPackage");
            String cacheConfigurationBeanPackage = SpringUtil.paramString("cacheConfigurationBeanPackage");
            String jdbcUrl = SpringUtil.paramString("jdbcUrl");
            String jdbcUsername = SpringUtil.paramString("jdbcUsername");
            String jdbcPassword = SpringUtil.paramString("jdbcPassword");
            String driverClassName = SpringUtil.paramString("driverClassName");
            String folder = SpringUtil.paramString("folder");

            Project project = businessService.findProjectById(id);
            if(project==null) return null;
            project.setRepositoryBeanPackage(repositoryBeanPackage);
            project.setDaoBeanPackage(daoBeanPackage);
            project.setTableBeanPackage(tableBeanPackage);
            project.setCacheConfigurationBeanPackage(cacheConfigurationBeanPackage);
            project.setJdbcUrl(jdbcUrl);
            project.setDriverClassName(driverClassName);
            project.setJdbcUsername(jdbcUsername);
            project.setJdbcPassword(jdbcPassword);
            project.setFolder(folder);
            businessService.updateProject(project);

            JdbcDaoGeneratorProperties properties = new JdbcDaoGeneratorProperties();
            properties.setTableBeanPackage(tableBeanPackage);
            properties.setDaoBeanPackage(daoBeanPackage);
            properties.setRepositoryBeanPackage(repositoryBeanPackage);
            properties.setCacheConfigurationBeanPackage(cacheConfigurationBeanPackage);
            properties.setOutputDirectory(project.getFolder());
            properties.setJdbcUrl(jdbcUrl);
            properties.setJdbcUsername(jdbcUsername);
            properties.setJdbcPassword(jdbcPassword);
            properties.setDriverClassName(driverClassName);
            properties.setOutputDirectory(folder);

            return JdbcDaoGenerator.run(properties);
        });
    }
}
