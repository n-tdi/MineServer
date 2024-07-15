package world.ntdi.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {
    @EventHandler
    public void onHunger(FoodLevelChangeEvent p_foodLevelChangeEvent) {
        p_foodLevelChangeEvent.setCancelled(true);
    }
}
