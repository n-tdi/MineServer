package world.ntdi.api.pet;

import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.api.Api;

import java.util.UUID;

public class PetServiceImpl implements PetService {
    private BukkitRunnable m_petSpawnLoop;


    @Override
    public void setupPetSpawnLoop() {
        m_petSpawnLoop = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };

        m_petSpawnLoop.runTaskTimer(Api.getInstance(), 0, 0);
    }

    @Override
    public void playerJoin(UUID p_uuid) {

    }

    @Override
    public void playerLeave(UUID p_uuid) {

    }

    @Override
    public void spawnPet(UUID p_uuid, int p_slot, Pets p_petType) {

    }

    @Override
    public void despawnPet(UUID p_uuid, int p_slot) {

    }

    @Override
    public void recalculatePets(UUID p_uuid) {

    }
}
