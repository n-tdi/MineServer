package world.ntdi.api.playerwrapper;

import lombok.Getter;
import world.ntdi.api.Api;
import world.ntdi.api.pet.CustomPet;
import world.ntdi.api.pet.Pets;
import world.ntdi.api.sql.entity.PlayerEntity;

import java.util.*;

public class PlayerWrapper {
    private final static Map<UUID, PlayerWrapper> PLAYER_WRAPPER_MAP = new HashMap<>();
    public static void playerJoin(UUID p_uuid) {
        PLAYER_WRAPPER_MAP.put(p_uuid, new PlayerWrapper(p_uuid));
    }

    public static void playerQuit(UUID p_uuid) {
        PLAYER_WRAPPER_MAP.remove(p_uuid);
    }

    public static PlayerWrapper getPlayerWrapper(UUID p_uuid) {
        return PLAYER_WRAPPER_MAP.get(p_uuid);
    }


    @Getter
    private final UUID m_uuid;
    @Getter
    private final List<CustomPet> m_pets;

    private final Api m_api;

    private PlayerWrapper(UUID p_uuid) {
        m_uuid = p_uuid;
        m_pets = Arrays.asList(new CustomPet[3]);
        m_api = Api.getInstance();

        final PlayerEntity playerEntity = m_api.getPlayerService().getPlayerOrDefault(p_uuid);
        m_pets.set(0, playerEntity.getPetSlot1() == null ? null : playerEntity.getPetSlot1().create());
        m_pets.set(1, playerEntity.getPetSlot2() == null ? null : playerEntity.getPetSlot2().create());
        m_pets.set(2, playerEntity.getPetSlot3() == null ? null : playerEntity.getPetSlot3().create());
    }

    public void setPet(int p_slot, Pets p_pet) {
        if (p_slot < 0 || p_slot >= m_pets.size()) {
            throw new IllegalArgumentException("Invalid slot");
        }
        final PlayerEntity playerEntity = m_api.getPlayerService().getPlayerOrDefault(getUuid());

        m_pets.set(p_slot, p_pet.create());

        switch (p_slot) {
            case 0:
                playerEntity.setPetSlot1(p_pet);
                break;
            case 1:
                playerEntity.setPetSlot2(p_pet);
                break;
            case 2:
                playerEntity.setPetSlot3(p_pet);
                break;
        }

        m_api.getPlayerService().savePlayer(playerEntity);
        m_api.getPetService().recalculatePets(getUuid());
    }

    public CustomPet getPet(int p_slot) {
        if (p_slot < 0 || p_slot >= m_pets.size()) {
            throw new IllegalArgumentException("Invalid slot");
        }

        return m_pets.get(p_slot);
    }
}
