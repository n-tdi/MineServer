package world.ntdi.api;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.api.sql.database.PostgresqlDatabase;

import java.sql.SQLException;


public final class Api extends JavaPlugin {
    @Getter
    private static PostgresqlDatabase m_postgresqlDatabase;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        initializeDatabase();
    }

    private void initializeDatabase() {
        final String sqlHost = getConfig().getString("sql-host");
        final String sqlPort = getConfig().getString("sql-port");
        final String sqlUser = getConfig().getString("sql-user");
        final String sqlPassword = getConfig().getString("sql-password");

        final String connectionString = "jdbc:postgresql://" + sqlHost + ":" + sqlPort + "/postgres";

        try {
            m_postgresqlDatabase = new PostgresqlDatabase(connectionString, sqlUser, sqlPassword);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void onDisable() {
        m_postgresqlDatabase.close();
    }
}
