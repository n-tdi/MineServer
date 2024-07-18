package world.ntdi.api.nametag;

import org.bukkit.entity.Player;

public interface PlayerNameTagService {
    void updatePlayerNameTag(Player p_player, String p_NameTag);
    String getPlayerNameTag(Player p_player);

    void initializePlayer(Player p_player);
    void removePlayer(Player p_player);

    void recalibratePlayer(Player p_player);
}
