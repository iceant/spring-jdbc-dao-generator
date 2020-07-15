package com.github.iceant.spring.jdbc.dao.generator.webapp.config;

import com.github.iceant.spring.jdbc.dao.generator.webapp.services.StorageService;
import com.github.iceant.spring.jdbc.dao.generator.webapp.utils.SQLiteUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    private final ApplicationContext applicationContext;

    public DataSourceConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Primary
    @Qualifier("dataSource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.sqlite.JDBC")
                .url("jdbc:sqlite://" + SQLiteUtil.getPath())
                .build();
    }

    @PostConstruct
    public void afterStartup() {
        StorageService storageService = applicationContext.getBean(StorageService.class);
        storageService.createDatabase();
    }
}
