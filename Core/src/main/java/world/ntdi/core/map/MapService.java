package world.ntdi.core.map;

import org.bukkit.Location;
import world.ntdi.api.region.Cuboid;

public interface MapService {
    Location getSpawn();
    void teleportAllPlayersToSpawn();
    void snapshotMap();
    void restoreMap();
    Cuboid getMapRegion();
}
