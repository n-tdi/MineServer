package world.ntdi.api.sql.service.services;

import com.j256.ormlite.dao.Dao;
import world.ntdi.api.sql.entity.PlayerEntity;

import java.util.UUID;

public interface PlayerService {
    Dao<PlayerEntity, UUID> getPlayerDao();
    PlayerEntity getPlayerOrDefault(UUID p_uuid);
    void savePlayer(PlayerEntity p_playerEntity);
    void deletePlayer(PlayerEntity p_playerEntity);

}
