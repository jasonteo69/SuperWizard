package com.superwizard.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final HikariDataSource dataSource;

    private DatabaseManager(String jdbcUrl, String username, String password) {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        if (username != null && !username.isEmpty()) cfg.setUsername(username);
        if (password != null && !password.isEmpty()) cfg.setPassword(password);
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(1);
        cfg.setPoolName("superwizard-pool");
        cfg.addDataSourceProperty("cachePrepStmts", "true");
        cfg.addDataSourceProperty("prepStmtCacheSize", "250");
        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.dataSource = new HikariDataSource(cfg);
    }

    public static synchronized void init(String jdbcUrl, String username, String password) {
        if (instance == null) {
            instance = new DatabaseManager(jdbcUrl, username, password);
            // Run Flyway migrations from classpath:db/migration
            Flyway flyway = Flyway.configure()
                    .dataSource(instance.getDataSource())
                    .locations("classpath:db/migration")
                    .load();
            flyway.migrate();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DatabaseManager not initialized. Call DatabaseManager.init(...) first.");
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
