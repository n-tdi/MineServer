package world.ntdi.api.sql.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.RequiredArgsConstructor;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.PlayerEntity;
import world.ntdi.api.sql.service.services.PlayerService;

import java.sql.SQLException;
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
    public PlayerEntity getPlayerOrDefault(UUID p_uuid) {
        try {
            if (m_playerDao.idExists(p_uuid)) {
                return m_playerDao.queryForId(p_uuid);
            } else {
                return new PlayerEntity(p_uuid, 0, 0);
            }
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void savePlayer(PlayerEntity p_playerEntity) {
        try {
            m_playerDao.createOrUpdate(p_playerEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deletePlayer(PlayerEntity p_playerEntity) {
        try {
            m_playerDao.delete(p_playerEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }
}
