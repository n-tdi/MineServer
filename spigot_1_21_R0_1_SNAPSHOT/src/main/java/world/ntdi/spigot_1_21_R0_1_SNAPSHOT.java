package world.ntdi;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import world.ntdi.api.nms.ChangePlayerNameService;

public class spigot_1_21_R0_1_SNAPSHOT implements ChangePlayerNameService {
    @Override
    public void updatePlayerUsername(String p_username, Player p_player) {
        CraftPlayer craftPlayer = (CraftPlayer) p_player;
        ServerPlayer serverPlayer = craftPlayer.getHandle();
    }
}
