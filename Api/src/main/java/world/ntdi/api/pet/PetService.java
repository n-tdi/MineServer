package world.ntdi.api.pet;

import java.util.UUID;

public interface PetService {
    void setupPetSpawnLoop();
    void playerJoin(UUID p_uuid);
    void playerLeave(UUID p_uuid);
}
