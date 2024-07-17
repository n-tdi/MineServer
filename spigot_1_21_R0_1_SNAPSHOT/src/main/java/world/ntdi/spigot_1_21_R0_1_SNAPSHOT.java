package world.ntdi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import world.ntdi.api.nms.ChangePlayerNameService;

public class spigot_1_21_R0_1_SNAPSHOT implements ChangePlayerNameService {
    @Override
    public void updatePlayerUsername(String p_username, Player p_player) {
        Bukkit.getLogger().info("IT WORKED!");
    }
}
