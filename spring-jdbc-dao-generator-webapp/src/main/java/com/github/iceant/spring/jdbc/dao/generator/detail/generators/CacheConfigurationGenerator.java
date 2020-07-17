package com.github.iceant.spring.jdbc.dao.generator.detail.generators;

import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGeneratorProperties;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBean;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.models.CacheConfigurationBeanModel;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.models.TableBeanModel;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.MustacheTemplateUtil;

import java.util.ArrayList;
import java.util.List;

public class CacheConfigurationGenerator {
    public static JavaBean toJavaBean(List<TableMeta> tableMetas, JdbcDaoGeneratorProperties properties) {
        List<TableBeanModel> tableBeanModels = new ArrayList<>();
        for (TableMeta tableMeta : tableMetas) {
            String packageName = properties.getTableBeanPackage();
            String tableName = tableMeta.getName();
            String beanClassName = GeneratorUtil.tableNameToBeanName(tableName);

            TableBeanModel tableBeanModel = new TableBeanModel(tableMeta);
            tableBeanModel.setPackageName(packageName);
            tableBeanModel.setClassName(beanClassName);

            tableBeanModels.add(tableBeanModel);
        }

        String packageName = properties.getCacheConfigurationBeanPackage();
        CacheConfigurationBeanModel configurationBeanModel = new CacheConfigurationBeanModel();
        configurationBeanModel.setPackageName(packageName);
        configurationBeanModel.setTableBeanPackage(properties.getTableBeanPackage());
        configurationBeanModel.setTableBeans(tableBeanModels);

        String code = MustacheTemplateUtil.render("spring/jdbc/CacheConfigurationBean.java", configurationBeanModel);
        JavaBean javaBean = new JavaBean();
        javaBean.setPackageName(packageName);
        javaBean.setClassName("CacheConfiguration");
        javaBean.setSourceCode(code);
        return javaBean;
    }
}
