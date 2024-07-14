package world.ntdi.core.listener;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import world.ntdi.core.map.MapService;

@AllArgsConstructor
public class PlayerMoveListener implements Listener {
    private final MapService m_mapService;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent p_playerMoveEvent) {
        if (p_playerMoveEvent.getTo() == p_playerMoveEvent.getFrom()) {
            return;
        }

        Bukkit.getLogger().info(m_mapService.getMapRegion().contains(p_playerMoveEvent.getTo()) + "");
    }
}
