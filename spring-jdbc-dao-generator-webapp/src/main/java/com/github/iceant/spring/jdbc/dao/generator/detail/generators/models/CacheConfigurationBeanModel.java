package com.github.iceant.spring.jdbc.dao.generator.detail.generators.models;

import java.util.List;

public class CacheConfigurationBeanModel {
    private String packageName;
    private String tableBeanPackage;
    private List<TableBeanModel> tableBeans;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTableBeanPackage() {
        return tableBeanPackage;
    }

    public void setTableBeanPackage(String tableBeanPackage) {
        this.tableBeanPackage = tableBeanPackage;
    }

    public List<TableBeanModel> getTableBeans() {
        return tableBeans;
    }

    public void setTableBeans(List<TableBeanModel> tableBeans) {
        this.tableBeans = tableBeans;
    }
}
