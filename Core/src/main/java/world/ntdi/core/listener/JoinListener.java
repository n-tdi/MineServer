package world.ntdi.core.listener;

import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.ntdi.core.item.MiniBomb;
import world.ntdi.core.map.MapService;

@AllArgsConstructor
public class JoinListener implements Listener {
    private final MapService m_mapService;

    @EventHandler
    public void onJoin(PlayerJoinEvent p_playerJoinEvent) {
        p_playerJoinEvent.setJoinMessage(null);

        final Player player = p_playerJoinEvent.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.teleport(m_mapService.getSpawn());

        final MiniBomb miniBomb = new MiniBomb(m_mapService);

        player.getInventory().addItem(miniBomb.getItemStack());
    }
}
