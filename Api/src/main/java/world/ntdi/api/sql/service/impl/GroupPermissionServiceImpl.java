package world.ntdi.api.sql.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.RequiredArgsConstructor;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.GroupEntity;
import world.ntdi.api.sql.entity.GroupPermissionEntity;
import world.ntdi.api.sql.service.services.GroupPermissionService;

import java.sql.SQLException;

@RequiredArgsConstructor
public class GroupPermissionServiceImpl implements GroupPermissionService {
    private final PostgresqlDatabase m_postgresqlDatabase;
    private Dao<GroupPermissionEntity, Integer> m_groupPermissionDao;

    @Override
    public Dao<GroupPermissionEntity, Integer> getPermissionDao() {
        if (m_groupPermissionDao != null) {
            return m_groupPermissionDao;
        }
        try {
            DaoManager.createDao(m_postgresqlDatabase.getConnectionSource(), GroupPermissionEntity.class);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
        return null;
    }

    @Override
    public void savePermission(GroupPermissionEntity p_groupPermissionEntity) {
        try {
            getPermissionDao().createOrUpdate(p_groupPermissionEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deletePermission(GroupPermissionEntity p_groupPermissionEntity) {
        try {
            getPermissionDao().delete(p_groupPermissionEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public GroupPermissionEntity createPermission(GroupEntity p_groupEntity, String p_permission) {
        final GroupPermissionEntity groupPermissionEntity = new GroupPermissionEntity();
        groupPermissionEntity.setGroupEntity(p_groupEntity);
        groupPermissionEntity.setPermission(p_permission);

        return groupPermissionEntity;
    }

    @Override
    public GroupPermissionEntity findByName(GroupEntity p_groupEntity, String p_permission) {
        QueryBuilder<GroupPermissionEntity, Integer> queryBuilder = getPermissionDao().queryBuilder();

        try {
            return queryBuilder.where().eq("group", p_groupEntity).and().eq("permission", p_permission).queryForFirst();
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }
}
