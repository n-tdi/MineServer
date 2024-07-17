package world.ntdi.qol.listener;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.ntdi.core.map.MapService;

@AllArgsConstructor
public class JoinListener implements Listener {
    private final MapService m_mapService;

    @EventHandler
    public void onJoin(PlayerJoinEvent p_playerJoinEvent) {
        final Player player = p_playerJoinEvent.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.teleport(m_mapService.getSpawn());

        player.setHealth(20);
        player.setFoodLevel(20);

        final String joinMessage = ChatColor.GRAY + "<" + ChatColor.GOLD + "+" + ChatColor.GRAY + "> " + player.getName();
        p_playerJoinEvent.setJoinMessage(joinMessage);


    }
}
