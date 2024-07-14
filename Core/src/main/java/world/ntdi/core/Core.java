package world.ntdi.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.api.command.CommandCL;
import world.ntdi.core.command.map.MapCommand;
import world.ntdi.core.listener.ExplosionListener;
import world.ntdi.core.listener.HungerListener;
import world.ntdi.core.listener.JoinListener;
import world.ntdi.core.listener.PlayerDamageListener;
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

        registerEvent(new ExplosionListener(m_mapService));
        registerEvent(new JoinListener(m_mapService));
        registerEvent(new PlayerDamageListener());
        registerEvent(new HungerListener());
    }

    private void registerEvent(Listener p_listener) {
        getServer().getPluginManager().registerEvents(p_listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
