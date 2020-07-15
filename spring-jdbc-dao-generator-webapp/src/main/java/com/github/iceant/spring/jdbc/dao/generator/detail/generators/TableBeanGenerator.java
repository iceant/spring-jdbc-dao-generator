package com.github.iceant.spring.jdbc.dao.generator.detail.generators;


import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGeneratorProperties;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBean;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.models.TableBeanModel;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.MustacheTemplateUtil;

public class TableBeanGenerator {

    public static JavaBean toJavaBean(TableMeta tableMeta, JdbcDaoGeneratorProperties properties){
        String packageName = properties.getTableBeanPackage();
        String tableName = tableMeta.getName();
        String beanClassName = GeneratorUtil.tableNameToBeanName(tableName);

        TableBeanModel tableBeanModel = new TableBeanModel(tableMeta);
        tableBeanModel.setPackageName(packageName);
        tableBeanModel.setClassName(beanClassName);

        String code = MustacheTemplateUtil.render("spring/jdbc/TableBean.java", tableBeanModel);
        JavaBean javaBean = new JavaBean();
        javaBean.setPackageName(packageName);
        javaBean.setClassName(beanClassName);
        javaBean.setSourceCode(code);
        return javaBean;
    }
}
