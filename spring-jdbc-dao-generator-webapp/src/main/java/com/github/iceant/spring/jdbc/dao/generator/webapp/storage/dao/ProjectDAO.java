package com.github.iceant.spring.jdbc.dao.generator.webapp.storage.dao;

import com.github.iceant.spring.jdbc.dao.generator.webapp.storage.bean.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectDAO {


    static final RowMapper<Project> ROW_MAPPER = new RowMapper<Project>() {
        @Override
        public Project mapRow(ResultSet resultSet, int i) throws SQLException {
            Project project = new Project();
            project.setId(resultSet.getLong("id"));
            project.setName(resultSet.getString("name"));
            project.setFolder(resultSet.getString("folder"));
            project.setDaoBeanPackage(resultSet.getString("daoBeanPackage"));
            project.setRepositoryBeanPackage(resultSet.getString("repositoryBeanPackage"));
            project.setTableBeanPackage(resultSet.getString("tableBeanPackage"));
            project.setCacheConfigurationBeanPackage(resultSet.getString("cacheConfigurationBeanPackage"));
            project.setJdbcUrl(resultSet.getString("jdbcUrl"));
            project.setDriverClassName(resultSet.getString("driverClassName"));
            project.setJdbcUsername(resultSet.getString("jdbcUsername"));
            project.setJdbcPassword(resultSet.getString("jdbcPassword"));
            return project;
        }
    };

    private final JdbcTemplate jdbcTemplate;

    public ProjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project insert(Project project) {
        jdbcTemplate.update("INSERT INTO project(id, name, folder) values(?, ?, ?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                StatementCreatorUtils.setParameterValue(preparedStatement, 1, StatementCreatorUtils.javaTypeToSqlParameterType(Long.class), project.getId());
                StatementCreatorUtils.setParameterValue(preparedStatement, 2, StatementCreatorUtils.javaTypeToSqlParameterType(String.class), project.getName());
                StatementCreatorUtils.setParameterValue(preparedStatement, 3, StatementCreatorUtils.javaTypeToSqlParameterType(String.class), project.getFolder());
            }
        });
        return project;
    }

    public Project update(Project project){
        int ret = jdbcTemplate.update("UPDATE project set name=?, folder=?, daoBeanPackage=?, repositoryBeanPackage=?, tableBeanPackage=?, cacheConfigurationBeanPackage=?, jdbcUrl=?, jdbcUsername=?, jdbcPassword=?, driverClassName=?  WHERE id=?",
                project.getName(),
                project.getFolder(),
                project.getDaoBeanPackage(),
                project.getRepositoryBeanPackage(),
                project.getTableBeanPackage(),
                project.getCacheConfigurationBeanPackage(),
                project.getJdbcUrl(),
                project.getJdbcUsername(),
                project.getJdbcPassword(),
                project.getDriverClassName(),
                project.getId());
        return project;
    }

    static final String LIST_ALL_SQL = "SELECT * FROM project";
    public List<Project> listAll() {
        return jdbcTemplate.query(LIST_ALL_SQL, ROW_MAPPER);
    }

    static final String DELETE_ALL_SQL = "DELETE FROM project";
    public Integer deleteAll() {
        return jdbcTemplate.update(DELETE_ALL_SQL);
    }

    static final String FIND_BY_PK_SQL = "SELECT * FROM project WHERE id=?";
    public Project findByPk(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_PK_SQL, ROW_MAPPER, id);
    }
}
