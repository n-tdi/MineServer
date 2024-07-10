package world.ntdi.api.sql.database;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.SQLException;

public class PostgresqlDatabase {
    @Getter
    private final HikariConfig m_hikariConfig;
    private final HikariDataSource m_hikariDataSource;
    @Getter
    private final ConnectionSource m_connectionSource;

    public PostgresqlDatabase(final String p_connectionUrl, final String p_username, final String p_password) throws SQLException {
        this.m_hikariConfig = new HikariConfig();

        m_hikariConfig.setJdbcUrl(p_connectionUrl);
        m_hikariConfig.setUsername(p_username);
        m_hikariConfig.setPassword(p_password);

        this.m_hikariDataSource = new HikariDataSource(m_hikariConfig);
        this.m_connectionSource = new DataSourceConnectionSource(m_hikariDataSource, m_hikariConfig.getJdbcUrl());
    }

    public void close() {
        try {
            m_hikariDataSource.close();
            m_connectionSource.close();
        } catch (Exception p_e) {
            throw new RuntimeException(p_e);
        }
    }
}