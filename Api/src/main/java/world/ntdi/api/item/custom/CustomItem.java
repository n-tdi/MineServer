package world.ntdi.api.item.custom;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.api.Api;

public abstract class CustomItem implements Listener {
    @Getter
    private final String m_name;
    @Getter
    private final String[] m_description;
    @Getter
    private final Material m_material;

    public CustomItem(String p_name, String[] p_description, Material p_material) {
        this.m_name = p_name;
        this.m_description = p_description;
        this.m_material = p_material;
        Bukkit.getServer().getPluginManager().registerEvents(this, Api.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent p_playerInteractEvent) {
        if (p_playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || p_playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            onLeftClick(p_playerInteractEvent);
            return;
        }
        onRightClick(p_playerInteractEvent);
    }

    public abstract void onLeftClick(PlayerInteractEvent e);
    public abstract void onRightClick(PlayerInteractEvent e);

}
