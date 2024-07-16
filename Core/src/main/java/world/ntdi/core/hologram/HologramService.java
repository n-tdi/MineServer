package world.ntdi.core.hologram;

import world.ntdi.api.hologram.Hologram;

public interface HologramService {
    void createResetHologram();
    void setupHologramLoop();
    Hologram getResetHologram();
    void resetMapResetTimer();
}
