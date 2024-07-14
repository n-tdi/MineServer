package world.ntdi.core.item;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.api.cooldown.Cooldown;
import world.ntdi.api.item.custom.CustomItem;
import world.ntdi.core.map.MapService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MiniBomb extends CustomItem {
    private UUID m_lastExplosionUUID;

    private final MapService m_mapService;
    private final Cooldown m_cooldown;

    public MiniBomb(MapService p_mapService) {
        super(1, ChatColor.RED + "Mini Bomb", new String[]{ChatColor.GRAY + "Aww, so cute!"}, Material.APPLE);

        m_mapService = p_mapService;
        m_cooldown = new Cooldown();
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
            final String cooldownMessage = ChatColor.AQUA + "" + m_cooldown.getCooldownRemaining(this, TimeUnit.SECONDS) + "s";
            p_player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(cooldownMessage));
            return;
        }

        final TNTPrimed tntPrimed = (TNTPrimed) p_location.getWorld().spawnEntity(p_location, EntityType.TNT);

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
