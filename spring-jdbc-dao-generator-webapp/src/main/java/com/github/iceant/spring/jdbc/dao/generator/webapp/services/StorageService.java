package com.github.iceant.spring.jdbc.dao.generator.webapp.services;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class StorageService {
    private final JdbcTemplate jdbcTemplate;

    public StorageService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkInitStatus(){
        try {
            Integer count = jdbcTemplate.query("SELECT count(id) FROM project", new ResultSetExtractor<Integer>() {
                @Override
                public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                    return 0;
                }
            });

            return true;
        }catch (Exception err){
            return false;
        }
    }

    public void createDatabase() {
        if(!checkInitStatus()){
            createTable("CREATE TABLE IF NOT EXISTS project(id integer primary key autoincrement, " +
                    "name varchar(255) not null unique, " +
                    "folder text, " +
                    "repositoryBeanPackage text, " +
                    "daoBeanPackage text, " +
                    "tableBeanPackage text, " +
                    "cacheConfigurationBeanPackage text, " +
                    "jdbcUrl text, " +
                    "driverClassName text)");
        }
    }

    public void createTable(String sql){
        jdbcTemplate.update(sql);
    }
}
