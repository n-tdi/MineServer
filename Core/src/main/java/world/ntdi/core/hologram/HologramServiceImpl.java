package world.ntdi.core.hologram;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.api.hologram.Hologram;
import world.ntdi.core.Core;
import world.ntdi.core.map.MapService;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class HologramServiceImpl implements HologramService{
    private Hologram m_mapResetHologram;
    private final MapService m_mapService;
    private final Core m_core;
    private long m_durationUntilReset;

    private final String m_prefix = ChatColor.AQUA + "Map Resetting in: " + ChatColor.DARK_AQUA;
    private final Location m_spawnLocation = new Location(Bukkit.getWorld("world"), 0.5, 136, -7.5);

    @Override
    public void createResetHologram() {
        m_mapResetHologram = new Hologram(m_prefix, m_spawnLocation, false);
        m_mapResetHologram.deleteOnServerClose();
        m_durationUntilReset = 10 * 60 * 1000;
    }

    @Override
    public void setupHologramLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (m_durationUntilReset <= 0) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Map has been reset!");
                    m_mapService.restoreMap();
                    m_durationUntilReset = 10 * 60 * 1000;
                }

                if (m_durationUntilReset <= 60 * 1000 && m_durationUntilReset % 1000 == 0) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Map will reset in " + convertMillisToReadableTime(m_durationUntilReset));
                }

                m_mapResetHologram.updateHologram(m_prefix + convertMillisToReadableTime(m_durationUntilReset));
                m_durationUntilReset -= 1000;
            }
        }.runTaskTimer(m_core, 0, 20);
    }

    @Nullable
    @Override
    public Hologram getResetHologram() {
        return m_mapResetHologram;
    }

    @Override
    public void resetMapResetTimer() {
        m_durationUntilReset = 10 * 60 * 1000;
    }

    private String convertMillisToReadableTime(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%dm %ds", minutes, seconds);
    }
}
