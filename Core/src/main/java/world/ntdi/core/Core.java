package world.ntdi.core;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.api.command.CommandCL;
import world.ntdi.api.item.custom.CustomItemRegister;
import world.ntdi.core.command.map.MapCommand;
import world.ntdi.core.item.MiniBomb;
import world.ntdi.core.item.SquidCannon;
import world.ntdi.core.listener.ExplosionListener;
import world.ntdi.core.listener.JoinListener;
import world.ntdi.core.listener.PlayerDamageListener;
import world.ntdi.core.map.MapService;
import world.ntdi.core.map.MapServiceImpl;


public final class Core extends JavaPlugin {
    @Getter
    private MapService m_mapService;

    @Override
    public void onEnable() {
        // Plugin startup logic
        m_mapService = new MapServiceImpl();

        m_mapService.snapshotMap();

        CommandCL.register(new MapCommand(m_mapService), "kaboom");

        registerEvent(new ExplosionListener(m_mapService));
        registerEvent(new JoinListener(m_mapService, this));
        registerEvent(new PlayerDamageListener());

        createCustomItems();
    }

    private void createCustomItems() {
        CustomItemRegister.registerCustomItem(new MiniBomb(m_mapService));
        CustomItemRegister.registerCustomItem(new SquidCannon(this));
    }

    private void registerEvent(Listener p_listener) {
        getServer().getPluginManager().registerEvents(p_listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
