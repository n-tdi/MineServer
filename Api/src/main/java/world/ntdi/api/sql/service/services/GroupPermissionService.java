package world.ntdi.api.sql.service.services;

import com.j256.ormlite.dao.Dao;
import world.ntdi.api.sql.entity.GroupEntity;
import world.ntdi.api.sql.entity.GroupPermissionEntity;

public interface GroupPermissionService {
    Dao<GroupPermissionEntity, Integer> getPermissionDao();

    void savePermission(GroupPermissionEntity p_groupPermissionEntity);
    void deletePermission(GroupPermissionEntity p_groupPermissionEntity);
    GroupPermissionEntity createPermission(GroupEntity p_groupEntity, String p_permission);
}
