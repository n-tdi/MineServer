package world.ntdi.api.nametag;

import org.bukkit.Location;
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
            initializePlayer(p_player);
            TextDisplay textDisplay2 = m_uuidItemDisplayMap.get(p_player.getUniqueId());
            textDisplay2.setText(p_NameTag);
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
        TextDisplay textDisplay = createTextDisplay(p_player.getLocation());
        m_uuidItemDisplayMap.put(p_player.getUniqueId(), textDisplay);
        p_player.addPassenger(textDisplay);
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

    private TextDisplay createTextDisplay(Location p_location) {
        TextDisplay textDisplay = p_location.getWorld().spawn(p_location, TextDisplay.class);
        textDisplay.setCustomNameVisible(true);
        textDisplay.setShadowRadius(3f);

        textDisplay.setText("");

        return textDisplay;
    }
}
