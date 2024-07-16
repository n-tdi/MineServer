package world.ntdi.api.pet;

import java.util.UUID;

public interface PetService {
    void setupPetSpawnLoop();
    void playerJoin(UUID p_uuid);
    void playerLeave(UUID p_uuid);
    void spawnPet(UUID p_uuid, int p_slot, Pets p_petType);
    void despawnPet(UUID p_uuid, int p_slot);
    void recalculatePets(UUID p_uuid);
}
