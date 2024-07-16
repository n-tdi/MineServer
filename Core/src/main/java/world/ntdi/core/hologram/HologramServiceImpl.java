package world.ntdi.core.hologram;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        final TextComponent mapHasBeenReset = Component
                .text("Map has been ").color(TextColor.color(0xd10023))
                .append(Component.text("reset!")
                        .color(TextColor.color(0x740B10))
                        .decorate(TextDecoration.BOLD)
                );

        final TextComponent mapWillResetSoon = Component
                .text("Map will reset in ").color(TextColor.color(0xd10023))
                .append(Component.text("1 minute!")
                        .color(TextColor.color(0x740B10))
                        .decorate(TextDecoration.BOLD)
                );

        new BukkitRunnable() {
            @Override
            public void run() {
                if (m_durationUntilReset <= 0) {
                    m_core.adventure().all().sendMessage(mapHasBeenReset);
                    m_mapService.teleportAllPlayersToSpawn();
                    m_mapService.restoreMap();
                    resetMapResetTimer();
                }

                if (m_durationUntilReset <= 60 * 1000 && m_durationUntilReset > 59 * 1000) {
                    m_core.adventure().all().sendMessage(mapWillResetSoon);
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
