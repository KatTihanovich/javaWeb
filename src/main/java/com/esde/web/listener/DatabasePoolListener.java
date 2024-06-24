package com.esde.web.listener;

import com.esde.web.pool.DatabasePool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabasePoolListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabasePool.initializeDataSource();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabasePool.closeDataSource();
    }
}