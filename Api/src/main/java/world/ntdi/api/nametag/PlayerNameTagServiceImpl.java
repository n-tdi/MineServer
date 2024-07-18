package world.ntdi.api.nametag;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlayerNameTagServiceImpl implements PlayerNameTagService {
    private final Map<UUID, TextDisplay> m_uuidItemDisplayMap;

    public PlayerNameTagServiceImpl() {
        m_uuidItemDisplayMap = new WeakHashMap<>();
    }

    @Override
    public void updatePlayerNameTag(Player p_player, String p_NameTag) {
        TextDisplay textDisplay = m_uuidItemDisplayMap.get(p_player.getUniqueId());

        if (textDisplay != null) {
            textDisplay.setText(p_NameTag);
        } else {
            throw new RuntimeException("Player not initialized");
        }
    }

    @Nullable
    @Override
    public String getPlayerNameTag(Player p_player) {
        TextDisplay textDisplay = m_uuidItemDisplayMap.get(p_player.getUniqueId());

        if (textDisplay != null) {
            return textDisplay.getText();
        }

        return null;
    }

    @Override
    public void initializePlayer(Player p_player) {
        final Location location = p_player.getLocation();

        final TextDisplay textDisplay = location.getWorld().spawn(location, TextDisplay.class);
        textDisplay.setCustomNameVisible(true);
        textDisplay.setCustomName("Hello World");
        textDisplay.setText("NameTag");
        textDisplay.setBillboard(Display.Billboard.CENTER);
        p_player.addPassenger(textDisplay);

        m_uuidItemDisplayMap.put(p_player.getUniqueId(), textDisplay);
    }

    @Override
    public void removePlayer(Player p_player) {
        TextDisplay textDisplay = m_uuidItemDisplayMap.get(p_player.getUniqueId());
        if (textDisplay != null) {
            textDisplay.remove();
        }
    }

    @Override
    public void recalibratePlayer(Player p_player) {
        TextDisplay textDisplay = m_uuidItemDisplayMap.get(p_player.getUniqueId());
        if (textDisplay != null) {
            p_player.addPassenger(textDisplay);
        }
    }
}
