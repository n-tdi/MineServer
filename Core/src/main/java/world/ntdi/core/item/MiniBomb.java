package world.ntdi.core.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.api.cooldown.Cooldown;
import world.ntdi.api.item.custom.CustomItem;
import world.ntdi.core.Core;
import world.ntdi.core.map.MapService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MiniBomb extends CustomItem implements Listener {
    private UUID m_lastExplosionUUID;

    private final MapService m_mapService;
    private final Cooldown m_cooldown;
    private final Core m_core;

    public MiniBomb(MapService p_mapService, Core p_core) {
        super(1, ChatColor.RED + "Mini Bomb", new String[]{ChatColor.GRAY + "Aww, so cute!"}, Material.APPLE);

        m_mapService = p_mapService;
        m_cooldown = new Cooldown();
        m_core = p_core;

        Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("Core"));
    }

    @Override
    public void onLeftClick(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        placeMiniBomb(p_player, p_player.getLocation());
    }

    @Override
    public void onRightClick(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        placeMiniBomb(p_player, p_player.getLocation());
    }

    private void placeMiniBomb(final Player p_player, final Location p_location) {
        if (!m_mapService.getMapRegion().contains(p_location)) {
            return;
        }

        if (m_cooldown.hasCooldown(this)) {
            final TextComponent message = Component.text(
                    m_cooldown.getCooldownRemaining(this, TimeUnit.SECONDS) + "s", NamedTextColor.AQUA);
            m_core.adventure().player(p_player).sendActionBar(message);
            return;
        }

        final TNTPrimed tntPrimed = (TNTPrimed) p_location.getWorld().spawnEntity(p_location, EntityType.TNT);
        tntPrimed.setSource(p_player);

        m_lastExplosionUUID = tntPrimed.getUniqueId();

        m_cooldown.setCooldown(this, 5, TimeUnit.SECONDS);
    }

    @EventHandler
    public void onPrime(ExplosionPrimeEvent p_explosionPrimeEvent) {
        if (p_explosionPrimeEvent.getEntityType() != EntityType.TNT) {
            return;
        }

        if (!p_explosionPrimeEvent.getEntity().getUniqueId().equals(m_lastExplosionUUID)) {
            return;
        }

        p_explosionPrimeEvent.setRadius(1F);
    }


}
