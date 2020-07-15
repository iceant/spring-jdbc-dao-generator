package com.github.iceant.spring.jdbc.dao.generator.detail;

import com.github.iceant.spring.jdbc.dao.generator.detail.generators.DaoBeanGenerator;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.RepositoryBeanGenerator;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.TableBeanGenerator;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBean;
import com.github.iceant.spring.jdbc.dao.generator.detail.generators.bean.JavaBeanUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.MetaUtil;
import com.github.iceant.spring.jdbc.dao.generator.detail.meta.TableMeta;
import com.github.iceant.spring.jdbc.dao.generator.detail.util.GeneratorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JdbcDaoGenerator {

    public static List<String> run(JdbcDaoGeneratorProperties properties) {
        String targetPath = properties.getOutputDirectory();

        SimpleDataSource dataSource = new SimpleDataSource(properties);

        List<String> files = new ArrayList<>();

        List<String> tableNames = GeneratorUtil.getTableNames(dataSource);
        for (String tableName : tableNames) {
            TableMeta tableMeta = MetaUtil.getTableMeta(dataSource, tableName);
            JavaBean javaBean = TableBeanGenerator.toJavaBean(tableMeta, properties);
            File tableBean = JavaBeanUtil.saveToPath(javaBean, new File(targetPath), true);
            files.add(tableBean.getAbsolutePath());

            JavaBean repoBean = RepositoryBeanGenerator.toJavaBean(tableMeta, properties);
            File repoBeanFile = JavaBeanUtil.saveToPath(repoBean, new File(targetPath), true);
            files.add(repoBeanFile.getAbsolutePath());

            JavaBean daoBean = DaoBeanGenerator.toJavaBean(tableMeta, properties);
            File daoBeanFile = JavaBeanUtil.saveToPath(daoBean, new File(targetPath), false);
            files.add(daoBeanFile.getAbsolutePath());
        }
        return files;
    }
}
