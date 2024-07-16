package world.ntdi.core.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.api.cooldown.Cooldown;
import world.ntdi.api.item.custom.CustomItem;
import world.ntdi.core.Core;

import java.util.concurrent.TimeUnit;

public class SquidCannon extends CustomItem implements Listener {
    private final Core m_core;
    private final Cooldown m_cooldown;

    public SquidCannon(Core p_core) {
        super(2, ChatColor.AQUA + "SQUID CANNON", new String[]{ChatColor.GRAY + "KABHEUBAHEGJLNAE"}, Material.CARROT_ON_A_STICK);
        m_core = p_core;
        m_cooldown = new Cooldown();
    }

    @Override
    public void onLeftClick(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        createSquid(p_player, p_playerInteractEvent);
    }

    @Override
    public void onRightClick(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        createSquid(p_player, p_playerInteractEvent);
    }

    private void createSquid(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        if (m_cooldown.hasCooldown(this)) {
            final TextComponent message = Component.text(
                    m_cooldown.getCooldownRemaining(this, TimeUnit.SECONDS) + "s", NamedTextColor.AQUA);
            m_core.adventure().player(p_player).sendActionBar(message);
            return;
        }

        final World world = p_player.getWorld();
        final Location location = p_player.getLocation();

        final Squid squid = world.spawn(location, Squid.class);
        squid.setVelocity(p_player.getLocation().getDirection().multiply(3));
        squid.setInvulnerable(true);
        squid.setCustomNameVisible(true);

        new BukkitRunnable() {
            private double m_lasting = 1.5;
            @Override
            public void run() {
                if (m_lasting > 0.1) {
                    m_lasting -= 0.1;
                    squid.setCustomName(ChatColor.AQUA + String.format("%.2g%n", m_lasting) + "s");
                } else {
                    squid.remove();
                    cancel();
                    world.createExplosion(location, 5, false, true, p_player);

                }
            }
        }.runTaskTimer(m_core, 0, 3);

        m_cooldown.setCooldown(this, 1, TimeUnit.SECONDS);

    }
}
