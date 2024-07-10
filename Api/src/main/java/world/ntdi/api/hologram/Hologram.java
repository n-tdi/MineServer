package world.ntdi.api.hologram;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.api.Api;
import world.ntdi.api.chat.ChatUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Hologram {
    public static NamespacedKey key = new NamespacedKey(Api.getInstance(), "Hologram");
    @Getter
    private UUID m_id;
    @Getter
    private final String[] m_text;
    @Getter
    private Location m_spawnLoc;
    @Getter
    private boolean m_baby;

    private final List<ArmorStand> m_armorStands;

    public Hologram(String[] text, Location spawnLoc, boolean baby) {
        this.m_id = UUID.randomUUID();
        this.m_text = text;
        this.m_spawnLoc = spawnLoc;
        this.m_baby = baby;
        this.m_armorStands = new LinkedList<>();

        initializeHolograms();
    }

    public Hologram(String text, Location spawnLoc, boolean baby) {
        this(new String[]{text}, spawnLoc, baby);

    }

    public Hologram(Location spawnLoc, boolean baby, String... text) {
        this(text, spawnLoc, baby);
    }

    private void initializeHolograms() {
        Location spawnLocClone = m_spawnLoc.clone();
        for (String s : m_text) {
            createHologram(s, spawnLocClone.subtract(0, 0.3, 0));
        }
    }

    private void createHologram(String name, Location location) {
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        as.setGravity(false);
        as.setInvisible(true);
        as.setInvulnerable(true);
        as.setSmall(m_baby);

        as.setCustomNameVisible(true);
        as.setCustomName(ChatUtils.chat(name));

        as.getPersistentDataContainer().set(Hologram.key, PersistentDataType.STRING, getId().toString());

        m_armorStands.add(as);
    }

    /**
     * Update the hologram's display text
     * @param name The new text for each line
     */
    public void updateHologram(String... name) {
        if (getText().length != name.length) {
            throw new ArrayIndexOutOfBoundsException("Cannot add lines to already defined length hologram");
        }
        for (int i = 0; i < m_armorStands.size(); i++) {
            m_armorStands.get(i).setCustomName(name[i]);
        }
    }

    /**
     * Update the hologram's location
     * @param location The new location for the hologram
     */
    public void updateHologram(Location location) {
        this.m_spawnLoc = location;
        for (int i = 0; i < m_armorStands.size(); i++) {
            m_armorStands.get(i).remove();
            createHologram(m_text[i], location.subtract(0, 0.3, 0));
        }
    }

    /**
     * Delete the entire hologram
     */
    public void deleteHologram() {
        getSpawnLoc().getWorld().getEntities().stream().forEach(entity -> {
            if (entity instanceof ArmorStand as) {
                if (as.getPersistentDataContainer().get(Hologram.key, PersistentDataType.STRING).equals(getId().toString())) {
                    entity.remove();
                }
            }
        });
    }

    /**
     * Adds this hologram to a deletion coroutine when the server closes.
     */
    public void deleteOnServerClose() {
        Api.toBeDeleted.add(this);
    }
}
