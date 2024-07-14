package world.ntdi.core.listener;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import world.ntdi.core.map.MapService;

@AllArgsConstructor
public class ExplosionListener implements Listener {
    private final MapService m_mapService;
    @EventHandler
    public void onExplode(EntityExplodeEvent p_entityExplodeEvent) {
        p_entityExplodeEvent.blockList().removeIf(block -> !m_mapService.getMapRegion().contains(block));


    }

    @EventHandler
    public void onDropItem(BlockDropItemEvent p_blockDropItemEvent) {
        // TODO: Code some cool stuff
    }
}
