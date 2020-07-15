package com.github.iceant.spring.jdbc.dao.generator.detail.generators;


import com.github.iceant.spring.jdbc.dao.generator.detail.JdbcDaoGeneratorProperties;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBean;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.models.DaoBeanModel;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.MustacheTemplateUtil;

public class DaoBeanGenerator {
    public static JavaBean toJavaBean(TableMeta tableMeta, JdbcDaoGeneratorProperties properties){
        String repositoryBeanPackageName = properties.getRepositoryBeanPackage();
        String daoBeanPackageName = properties.getDaoBeanPackage();
        String tableBeanPackageName = properties.getTableBeanPackage();

        String tableName = tableMeta.getName();
        String tableBeanClassName = GeneratorUtil.tableNameToBeanName(tableName);
        String daoBeanClassName = tableBeanClassName+"DAO";

        DaoBeanModel model = new DaoBeanModel(tableMeta);
        model.setPackageName(daoBeanPackageName);
        model.setRepositoryBeanPackageName(repositoryBeanPackageName);
        model.setTableBeanPackageName(tableBeanPackageName);
        model.setDaoBeanClassName(daoBeanClassName);

        String code = MustacheTemplateUtil.render("spring/jdbc/DaoBean.java", model);
        JavaBean javaBean = new JavaBean();
        javaBean.setPackageName(daoBeanPackageName);
        javaBean.setClassName(daoBeanClassName);
        javaBean.setSourceCode(code);
        return javaBean;
    }
}
