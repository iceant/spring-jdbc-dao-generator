package com.github.iceant.spring.jdbc.dao.generator.webapp.storage.bean;

public class Project {
    private Long id;
    private String name;
    private String folder;
    private String repositoryBeanPackage;
    private String daoBeanPackage;
    private String tableBeanPackage;
    private String cacheConfigurationBeanPackage;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private String driverClassName;

    public String getCacheConfigurationBeanPackage() {
        return cacheConfigurationBeanPackage;
    }

    public void setCacheConfigurationBeanPackage(String cacheConfigurationBeanPackage) {
        this.cacheConfigurationBeanPackage = cacheConfigurationBeanPackage;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getRepositoryBeanPackage() {
        return repositoryBeanPackage;
    }

    public void setRepositoryBeanPackage(String repositoryBeanPackage) {
        this.repositoryBeanPackage = repositoryBeanPackage;
    }

    public String getDaoBeanPackage() {
        return daoBeanPackage;
    }

    public void setDaoBeanPackage(String daoBeanPackage) {
        this.daoBeanPackage = daoBeanPackage;
    }

    public String getTableBeanPackage() {
        return tableBeanPackage;
    }

    public void setTableBeanPackage(String tableBeanPackage) {
        this.tableBeanPackage = tableBeanPackage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
