package world.ntdi.core.listener;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import world.ntdi.api.nametag.PlayerNameTagService;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {
    private final PlayerNameTagService m_playerNameTagService;

    @EventHandler
    public void onQuit(PlayerQuitEvent p_playerQuitEvent) {
        m_playerNameTagService.removePlayer(p_playerQuitEvent.getPlayer());
    }
}
