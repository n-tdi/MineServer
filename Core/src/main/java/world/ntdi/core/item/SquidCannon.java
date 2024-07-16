package world.ntdi.core.item;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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

    }

    @Override
    public void onRightClick(Player p_player, PlayerInteractEvent p_playerInteractEvent) {

    }

    private void createSquid(Player p_player, PlayerInteractEvent p_playerInteractEvent) {
        if (m_cooldown.hasCooldown(this)) {
            final String cooldownMessage = ChatColor.AQUA + "" + m_cooldown.getCooldownRemaining(this, TimeUnit.SECONDS) + "s";
            p_player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(cooldownMessage));
            return;
        }

        final World world = p_player.getWorld();
        final Location location = p_player.getLocation();

        final Squid squid = world.spawn(location, Squid.class);
        squid.setVelocity(p_player.getLocation().getDirection().multiply(2));
        squid.setInvulnerable(true);
        squid.setCustomNameVisible(true);

        new BukkitRunnable() {
            private double m_lasting = 3;
            @Override
            public void run() {
                if (m_lasting > 0) {
                    m_lasting -= 0.1;
                } else {
                    squid.remove();
                    cancel();

                    world.createExplosion(location, 5, false, true, p_player);
                }

                squid.setCustomName(ChatColor.AQUA.toString() + m_lasting + "s");
            }
        }.runTaskTimer(m_core, 0, 3);

        m_cooldown.setCooldown(this, 10, TimeUnit.SECONDS);

    }
}
