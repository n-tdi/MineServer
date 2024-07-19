package world.ntdi.api.sql.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.RequiredArgsConstructor;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.PlayerEntity;
import world.ntdi.api.sql.entity.PlayerPermissionEntity;
import world.ntdi.api.sql.service.services.PlayerPermissionService;

import java.sql.SQLException;

@RequiredArgsConstructor
public class PlayerPermissionServiceImpl implements PlayerPermissionService {
    private final PostgresqlDatabase m_postgresqlDatabase;
    private Dao<PlayerPermissionEntity, Integer> m_playerPermissionDao;

    @Override
    public PlayerPermissionEntity createPermission(PlayerEntity p_playerEntity, String p_permission) {
        PlayerPermissionEntity playerPermissionEntity = new PlayerPermissionEntity();
        playerPermissionEntity.setPlayerEntity(p_playerEntity);
        playerPermissionEntity.setPermission(p_permission);
        return playerPermissionEntity;
    }

    @Override
    public void deletePermission(PlayerPermissionEntity p_playerPermissionEntity) {
        try {
            getPermissionDao().delete(p_playerPermissionEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deletePermission(PlayerEntity p_playerEntity, String p_permission) {
        PlayerPermissionEntity playerPermissionEntity = p_playerEntity.getPlayerPermissionEntities().stream().reduce((p_first, p_second) -> p_second).orElse(null);
        if (p_playerEntity == null) {
            return;
        }

        try {
            getPermissionDao().delete(playerPermissionEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void savePermission(PlayerPermissionEntity p_playerPermissionEntity) {
        try {
            getPermissionDao().createOrUpdate(p_playerPermissionEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public Dao<PlayerPermissionEntity, Integer> getPermissionDao() {
        if (m_playerPermissionDao != null) {
            return m_playerPermissionDao;
        }

        try {
            return DaoManager.createDao(m_postgresqlDatabase.getConnectionSource(), PlayerPermissionEntity.class);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }
}
