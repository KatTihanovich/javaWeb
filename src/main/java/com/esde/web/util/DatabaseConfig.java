package com.esde.web.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/Java";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "159357";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    DatabaseConfig() {
    }

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DatabaseConfig.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(URL);
                    ds.setUsername(USERNAME); ///читать из файла
                    ds.setPassword(PASSWORD);
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

    //нужен доп метод
}