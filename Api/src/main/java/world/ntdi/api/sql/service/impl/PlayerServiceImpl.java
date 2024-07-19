package world.ntdi.api.sql.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.RequiredArgsConstructor;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.PlayerEntity;
import world.ntdi.api.sql.entity.PlayerPermissionEntity;
import world.ntdi.api.sql.service.services.PlayerService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PostgresqlDatabase m_postgresqlDatabase;
    private Dao<PlayerEntity, UUID> m_playerDao;

    @Override
    public Dao<PlayerEntity, UUID> getPlayerDao() {
        if (m_playerDao != null) {
            return m_playerDao;
        }

        try {
            return DaoManager.createDao(m_postgresqlDatabase.getConnectionSource(), PlayerEntity.class);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public List<String> getAllPermissions(PlayerEntity p_playerEntity) {
        return p_playerEntity.getPlayerPermissionEntities().stream().map(PlayerPermissionEntity::getPermission).toList();
    }

    @Override
    public PlayerEntity getPlayerOrDefault(UUID p_uuid) {
        try {
            if (getPlayerDao().idExists(p_uuid)) {
                return getPlayerDao().queryForId(p_uuid);
            } else {
                PlayerEntity playerEntity = new PlayerEntity();
                playerEntity.setUuid(p_uuid);
                playerEntity.setExperience(0);
                playerEntity.setCurrency(0);
//                playerEntity.setGroupEntity();  TODO: Set default group
                return playerEntity;
            }
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void savePlayer(PlayerEntity p_playerEntity) {
        try {
            getPlayerDao().createOrUpdate(p_playerEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deletePlayer(PlayerEntity p_playerEntity) {
        try {
            getPlayerDao().delete(p_playerEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public String getPrefix(PlayerEntity p_playerEntity) {
        if (p_playerEntity.getPrefix() != null) {
            return p_playerEntity.getPrefix();
        }

        if (p_playerEntity.getGroupEntity() != null && p_playerEntity.getGroupEntity().getPrefix() != null) {
            return p_playerEntity.getGroupEntity().getPrefix();
        }
        return "";
    }
}
