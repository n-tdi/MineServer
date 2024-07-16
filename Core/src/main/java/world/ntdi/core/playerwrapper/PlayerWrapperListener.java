package world.ntdi.core.playerwrapper;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import world.ntdi.core.Core;

@AllArgsConstructor
public class PlayerWrapperListener implements Listener {
    private final Core m_core;
    @EventHandler
    public void onJoin(PlayerJoinEvent p_playerJoinEvent) {
        PlayerWrapper.playerJoin(p_playerJoinEvent.getPlayer().getUniqueId(), m_core);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent p_playerQuitEvent) {
        PlayerWrapper.playerQuit(p_playerQuitEvent.getPlayer().getUniqueId());
    }
}
