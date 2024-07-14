package world.ntdi.core;

import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.api.command.CommandCL;
import world.ntdi.core.command.map.MapCommand;
import world.ntdi.core.listener.ExplosionListener;
import world.ntdi.core.map.MapService;
import world.ntdi.core.map.MapServiceImpl;


public final class Core extends JavaPlugin {
    private MapService m_mapService;

    @Override
    public void onEnable() {
        // Plugin startup logic
        m_mapService = new MapServiceImpl();

        m_mapService.snapshotMap();

        CommandCL.register(new MapCommand(m_mapService), "kaboom");

        getServer().getPluginManager().registerEvents(new ExplosionListener(m_mapService), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
