package com.github.iceant.spring.jdbc.dao.generator.webapp.utils;

import com.github.iceant.spring.jdbc.dao.generator.webapp.SpringJdbcDaoGeneratorWebappApplication;

import java.io.File;
import java.nio.file.Paths;

public class SQLiteUtil {
    public static String getPath(){
        File appHome =  AppUtil.getAppHome(SpringJdbcDaoGeneratorWebappApplication.class);
        return Paths.get(appHome.getAbsolutePath(), "jdbc-dao-generator.db").toString().toLowerCase();
    }
}
