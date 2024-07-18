package world.ntdi.api.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public final class PlayerUtil {
    public static void teleportWithPassengers(Player p_player, Location p_location) {
        List<Entity> passengers = p_player.getPassengers();
        passengers.forEach(p_player::removePassenger);
        p_player.teleport(p_location);
        passengers.forEach(p_player::addPassenger);
    }
}
