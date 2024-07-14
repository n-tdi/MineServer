package world.ntdi.core.listener;

import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent p_entityDamageEvent) {
        if (p_entityDamageEvent.getEntity() instanceof Player) {
            if (p_entityDamageEvent.getDamageSource().getDamageType() != DamageType.GENERIC
                    || p_entityDamageEvent.getDamageSource().getDamageType() != DamageType.GENERIC_KILL) {
                p_entityDamageEvent.setCancelled(true);
            }
        }
    }
}
