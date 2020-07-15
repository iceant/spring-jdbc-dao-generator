package com.github.iceant.spring.jdbc.dao.generator.detail;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

public class SimpleDataSource implements DataSource {
    final JdbcDaoGeneratorProperties properties;

    public SimpleDataSource(JdbcDaoGeneratorProperties properties) {
        this.properties = properties;
        Driver driver = null;
        try {
            Class<Driver> driverClass = (Class<Driver>) Class.forName(properties.getDriverClassName());
            driver = driverClass.newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {

        String username = properties.getJdbcUsername();
        String password = properties.getJdbcPassword();
        String jdbcUrl = properties.getJdbcUrl();

        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        String jdbcUrl = properties.getJdbcUrl();
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
