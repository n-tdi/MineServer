package world.ntdi.core.playerwrapper;

import lombok.Getter;
import world.ntdi.api.pet.CustomPet;
import world.ntdi.core.Core;

import java.util.*;

public class PlayerWrapper {
    private final static Map<UUID, PlayerWrapper> PLAYER_WRAPPER_MAP = new HashMap<>();
    public static void playerJoin(UUID p_uuid, Core p_core) {
        PLAYER_WRAPPER_MAP.put(p_uuid, new PlayerWrapper(p_core, p_uuid));
    }

    public static void playerQuit(UUID p_uuid) {
        PLAYER_WRAPPER_MAP.remove(p_uuid);
    }

    public static PlayerWrapper getPlayerWrapper(UUID p_uuid) {
        return PLAYER_WRAPPER_MAP.get(p_uuid);
    }

    private final Core m_core;

    @Getter
    private final UUID m_uuid;


    private final List<CustomPet> m_pets;

    private PlayerWrapper(Core p_core, UUID p_uuid) {
        m_core = p_core;
        m_uuid = p_uuid;



        m_pets = Arrays.asList();
    }
}
