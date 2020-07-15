package com.github.iceant.spring.jdbc.dao.generator.detail.generators.models;


import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;

public class DaoBeanModel {
    private TableMeta tableMeta;
    private String packageName;
    private String repositoryBeanPackageName;
    private String tableBeanPackageName;
    private String daoBeanClassName;

    public String getDaoBeanClassName() {
        return daoBeanClassName;
    }

    public void setDaoBeanClassName(String daoBeanClassName) {
        this.daoBeanClassName = daoBeanClassName;
    }

    public DaoBeanModel(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    public TableMeta getTableMeta() {
        return tableMeta;
    }

    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRepositoryBeanPackageName() {
        return repositoryBeanPackageName;
    }

    public void setRepositoryBeanPackageName(String repositoryBeanPackageName) {
        this.repositoryBeanPackageName = repositoryBeanPackageName;
    }

    public String getTableBeanPackageName() {
        return tableBeanPackageName;
    }

    public void setTableBeanPackageName(String tableBeanPackageName) {
        this.tableBeanPackageName = tableBeanPackageName;
    }

    public String getTableBeanClassName(){
        return GeneratorUtil.tableNameToBeanName(tableMeta.getName());
    }

    public String getRepositoryBeanClassName(){
        return getTableBeanClassName()+"Repository";
    }
}
