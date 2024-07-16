package world.ntdi.api.pet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import world.ntdi.api.Api;
import world.ntdi.api.playerwrapper.PlayerWrapper;

import java.util.*;

public class PetServiceImpl implements PetService {
    private BukkitRunnable m_petSpawnLoop;
    private final Map<UUID, List<ArmorStand>> m_spawnedPets;

    public PetServiceImpl() {
        m_spawnedPets = new WeakHashMap<>();
    }

    @Override
    public void setupPetSpawnLoop() {
        m_petSpawnLoop = new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : m_spawnedPets.keySet()) {
                    List<ArmorStand> armorStands = m_spawnedPets.get(uuid);
                    if (isEmpty(armorStands)) {
                        continue;
                    }

                    Player player = Bukkit.getPlayer(uuid);

                    if (armorStands.get(0) != null) {
                        armorStands.get(0).teleport(getSlot1Position(player));
                    }
                    if (armorStands.get(1) != null) {
                        armorStands.get(1).teleport(getSlot2Position(player));
                    }
                    if (armorStands.get(2) != null) {
                        armorStands.get(2).teleport(getSlot3Position(player));
                    }
                }
            }
        };

        m_petSpawnLoop.runTaskTimer(Api.getInstance(), 0, 0);
    }

    @Override
    public void playerJoin(UUID p_uuid) {
        m_spawnedPets.put(p_uuid, Arrays.asList(new ArmorStand[3]));
        recalculatePets(p_uuid);
    }

    @Override
    public void playerLeave(UUID p_uuid) {
        m_spawnedPets.remove(p_uuid);
    }

    @Override
    public void spawnPet(UUID p_uuid, int p_slot, Pets p_petType) {
        List<ArmorStand> armorStands = m_spawnedPets.get(p_uuid);
        Location location = null;
        final Player player = Bukkit.getPlayer(p_uuid);

        if (player == null) {
            throw new RuntimeException("Player not found");
        }

        location = switch (p_slot) {
            case 0 -> getSlot1Position(player);
            case 1 -> getSlot2Position(player);
            case 2 -> getSlot3Position(player);
            default -> location;
        };

        if (location == null) {
            throw new RuntimeException("Invalid slot");
        }

        CustomPet customPet = p_petType.create();

        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setCustomName(customPet.getName());
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);

        armorStands.set(p_slot, armorStand);
    }

    @Override
    public void despawnPet(UUID p_uuid, int p_slot) {
        List<ArmorStand> armorStands = m_spawnedPets.get(p_uuid);
        ArmorStand armorStand = armorStands.get(p_slot);
        armorStand.remove();
        armorStands.set(p_slot, null);
    }

    @Override
    public void recalculatePets(UUID p_uuid) {
        despawnPet(p_uuid, 0);
        despawnPet(p_uuid, 1);
        despawnPet(p_uuid, 2);

        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayerWrapper(p_uuid);
        final List<CustomPet> pets = playerWrapper.getPets();
        if (pets.get(0) != null) {
            spawnPet(p_uuid, 0, pets.get(0).getPetType());
        }
        if (pets.get(1) != null) {
            spawnPet(p_uuid, 1, pets.get(1).getPetType());
        }
        if (pets.get(2) != null) {
            spawnPet(p_uuid, 2, pets.get(2).getPetType());
        }
    }

    public Location getSlot1Position(Player player) {
        Vector direction = player.getLocation().getDirection();

        // Calculate the location one block behind and one block to the right
        Vector right = direction.clone().crossProduct(new Vector(0, 1, 0)).normalize();
        Vector behind = direction.clone().multiply(-1).normalize();

        return player.getLocation().add(right).add(behind);
    }

    public Location getSlot2Position(Player player) {
        Vector direction = player.getLocation().getDirection();

        // Calculate the location one block behind
        Vector behind = direction.clone().multiply(-1.5).normalize();

        return player.getLocation().add(behind);
    }

    public Location getSlot3Position(Player player) {
        Vector direction = player.getLocation().getDirection();

        // Calculate the location one block behind and one block to the left
        Vector left = direction.clone().crossProduct(new Vector(0, -1, 0)).normalize();
        Vector behind = direction.clone().multiply(-1).normalize();

        return player.getLocation().add(left).add(behind);
    }

    private boolean isEmpty(List<ArmorStand> p_armorStands) {
        boolean empty = true;
        for (ArmorStand armorStand : p_armorStands) {
            if (armorStand != null) {
                empty = false;
                break;
            }
        }
        return empty;
    }
}
