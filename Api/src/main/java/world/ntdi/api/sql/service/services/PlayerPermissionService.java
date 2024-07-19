package world.ntdi.api.sql.service.services;

import com.j256.ormlite.dao.Dao;
import world.ntdi.api.sql.entity.PlayerEntity;
import world.ntdi.api.sql.entity.PlayerPermissionEntity;

public interface PlayerPermissionService {
    PlayerPermissionEntity createPermission(PlayerEntity p_playerEntity, String p_permission);
    void deletePermission(PlayerPermissionEntity p_playerPermissionEntity);
    void deletePermission(PlayerEntity p_playerEntity, String p_permission);
    void savePermission(PlayerPermissionEntity p_playerPermissionEntity);
    Dao<PlayerPermissionEntity, Integer> getPermissionDao();
}
