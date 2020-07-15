package com.github.iceant.spring.jdbc.dao.generator.detail.generators;

import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGeneratorProperties;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBean;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.models.RepositoryBeanModel;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.MustacheTemplateUtil;

// 每个表格一个 RepositoryBean
public class RepositoryBeanGenerator {
    public static JavaBean toJavaBean(TableMeta tableMeta, JdbcDaoGeneratorProperties properties){
        String packageName = properties.getRepositoryBeanPackage();
        String tableBeanPackageName = properties.getTableBeanPackage();
        String tableName = tableMeta.getName();
        String tableBeanClassName = GeneratorUtil.tableNameToBeanName(tableName);
        String reposirotyBeanClassName = tableBeanClassName+"Repository";

        RepositoryBeanModel model = new RepositoryBeanModel(tableMeta);
        model.setPackageName(packageName);
        model.setClassName(reposirotyBeanClassName);
        model.setTableBeanPackageName(tableBeanPackageName);
        model.setTableBeanClassName(tableBeanClassName);

        String code = MustacheTemplateUtil.render("spring/jdbc/RepositoryBean.java", model);
        JavaBean javaBean = new JavaBean();
        javaBean.setPackageName(packageName);
        javaBean.setClassName(reposirotyBeanClassName);
        javaBean.setSourceCode(code);
        return javaBean;
    }
}
