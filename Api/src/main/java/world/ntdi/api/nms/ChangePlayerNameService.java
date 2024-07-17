package world.ntdi.api.nms;

import org.bukkit.entity.Player;

public interface ChangePlayerNameService {
    void updatePlayerUsername(String p_username, Player p_player);
}
