package world.ntdi.api;

import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.api.hologram.Hologram;
import world.ntdi.api.item.custom.CustomItemListener;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.PlayerEntity;
import world.ntdi.api.sql.service.impl.PlayerServiceImpl;
import world.ntdi.api.sql.service.services.PlayerService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class Api extends JavaPlugin {
    @Getter
    private static PostgresqlDatabase m_postgresqlDatabase;
    @Getter
    private PlayerService m_playerService;

    private BukkitAudiences m_adventure;


    @Override
    public void onEnable() {
        // Plugin startup logic
        m_adventure = BukkitAudiences.create(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        try {
            initializeDatabase();
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }

        getServer().getPluginManager().registerEvents(new CustomItemListener(), this);
    }

    private void initializeDatabase() throws SQLException {
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

        TableUtils.createTableIfNotExists(m_postgresqlDatabase.getConnectionSource(), PlayerEntity.class);

        m_playerService = new PlayerServiceImpl(m_postgresqlDatabase);
    }

    @Override
    public void onDisable() {
        m_postgresqlDatabase.close();

        if (!toBeDeleted.isEmpty()) {
            toBeDeleted.forEach(Hologram::deleteHologram);
        }
    }

    public static Api getInstance(){
        return (Api) Bukkit.getPluginManager().getPlugin("Api");
    }

    public static List<Hologram> toBeDeleted = new ArrayList<>();

    public @NonNull BukkitAudiences adventure() {
        if(m_adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return m_adventure;
    }
}
