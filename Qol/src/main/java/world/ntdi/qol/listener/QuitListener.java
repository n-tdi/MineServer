package world.ntdi.qol.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent p_playerQuitEvent) {
        final Player player = p_playerQuitEvent.getPlayer();

        final String quitMessage = ChatColor.GRAY + "<" + ChatColor.DARK_AQUA + "-" + ChatColor.GRAY + "> " + player.getName();
        p_playerQuitEvent.setQuitMessage(quitMessage);
    }
}
