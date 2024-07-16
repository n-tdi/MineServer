package world.ntdi.core.listener;

import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent p_entityDamageByEntityEvent) {
        if (p_entityDamageByEntityEvent.getEntity() instanceof Player p_player) {
            if (p_entityDamageByEntityEvent.getDamageSource().getDamageType() != DamageType.GENERIC
                    || p_entityDamageByEntityEvent.getDamageSource().getDamageType() != DamageType.GENERIC_KILL) {
                if (p_entityDamageByEntityEvent.getDamager() instanceof TNTPrimed p_tntPrimed) {
                    if (p_tntPrimed.getSource().getUniqueId().equals(p_player.getUniqueId())) {
                        p_entityDamageByEntityEvent.setDamage(0.0F);
                        return;
                    }
                }
                p_entityDamageByEntityEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent p_entityDamageEvent) {
        if (p_entityDamageEvent.getDamageSource().getDamageType() == DamageType.FALL) {
            p_entityDamageEvent.setCancelled(true);
        }
    }
}
