package com.github.iceant.spring.jdbc.dao.generator.detail;

public class JdbcDaoGeneratorProperties {
    private String repositoryBeanPackage;
    private String daoBeanPackage;
    private String tableBeanPackage;
    private String outputDirectory;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private String driverClassName;

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

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
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
}
