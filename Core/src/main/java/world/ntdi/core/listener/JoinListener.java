package world.ntdi.core.listener;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.ntdi.core.Core;
import world.ntdi.core.item.MiniBomb;
import world.ntdi.core.item.SquidCannon;
import world.ntdi.core.map.MapService;

@AllArgsConstructor
public class JoinListener implements Listener {
    private final MapService m_mapService;
    private final Core m_core;

    @EventHandler
    public void onJoin(PlayerJoinEvent p_playerJoinEvent) {
        final Player player = p_playerJoinEvent.getPlayer();

        final MiniBomb miniBomb = new MiniBomb(m_mapService, m_core);
        final SquidCannon squidCannon = new SquidCannon(m_core);

        player.getInventory().addItem(miniBomb.getItemStack(), squidCannon.getItemStack());
    }


}
