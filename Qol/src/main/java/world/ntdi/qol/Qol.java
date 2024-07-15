package world.ntdi.qol;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.core.Core;
import world.ntdi.qol.listener.HungerListener;
import world.ntdi.qol.listener.JoinListener;

public final class Qol extends JavaPlugin {
    private Core m_core;

    @Override
    public void onEnable() {
        // Plugin startup logic

        m_core = (Core) getServer().getPluginManager().getPlugin("Core");

        registerEvent(new HungerListener());
        registerEvent(new JoinListener(m_core.getMapService()));
    }

    private void registerEvent(Listener p_listener) {
        getServer().getPluginManager().registerEvents(p_listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
