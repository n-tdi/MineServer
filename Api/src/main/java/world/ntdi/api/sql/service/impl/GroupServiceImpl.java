package world.ntdi.api.sql.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.RequiredArgsConstructor;
import world.ntdi.api.sql.database.PostgresqlDatabase;
import world.ntdi.api.sql.entity.GroupEntity;
import world.ntdi.api.sql.entity.GroupPermissionEntity;
import world.ntdi.api.sql.service.services.GroupPermissionService;
import world.ntdi.api.sql.service.services.GroupService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final PostgresqlDatabase m_postgresqlDatabase;
    private final GroupPermissionService m_groupPermissionService;
    private Dao<GroupEntity, Integer> m_groupEntitiesDao;

    @Override
    public Dao<GroupEntity, Integer> getGroupDao() {
        if (m_groupEntitiesDao != null) {
            return m_groupEntitiesDao;
        }

        try {
            DaoManager.createDao(m_postgresqlDatabase.getConnectionSource(), GroupEntity.class);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
        return null;
    }

    @Override
    public GroupEntity getGroupOrDefault(String p_name) {
        QueryBuilder<GroupEntity, Integer> queryBuilder = getGroupDao().queryBuilder();
        try {
            GroupEntity queried = queryBuilder.where().eq("name", p_name).queryForFirst();
            if (queried == null) {
                GroupEntity groupEntity = new GroupEntity();
                groupEntity.setName(p_name);
                return groupEntity;
            } else {
                return queried;
            }
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public GroupEntity addPermission(GroupEntity p_groupEntity, String p_permission) {
        GroupPermissionEntity groupPermissionEntity = m_groupPermissionService.createPermission(p_groupEntity, p_permission);

        try {
            m_groupPermissionService.savePermission(groupPermissionEntity);
        } catch (Exception p_e) {
            throw new RuntimeException(p_e);
        }

        return p_groupEntity;
    }

    @Override
    public void saveGroup(GroupEntity p_groupEntity) {
        try {
            getGroupDao().createOrUpdate(p_groupEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deleteGroup(GroupEntity p_groupEntity) {
        try {
            getGroupDao().delete(p_groupEntity);
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }

    @Override
    public void deletePermission(GroupEntity p_groupEntity, String p_permission) {
        GroupPermissionEntity groupPermissionEntity = m_groupPermissionService.findByName(p_groupEntity, p_permission);
        if (groupPermissionEntity == null) {
            return;
        }

        m_groupPermissionService.deletePermission(groupPermissionEntity);
    }

    @Override
    public List<String> getAllPermissions(GroupEntity p_groupEntity) {
        final List<String> permissions = new ArrayList<>();
        p_groupEntity.getGroupPermissionEntities().stream().map(GroupPermissionEntity::getPermission).forEach(permissions::add);
        return permissions;
    }

    @Override
    public List<GroupEntity> getAllGroups() {
        try {
            return getGroupDao().queryForAll();
        } catch (SQLException p_e) {
            throw new RuntimeException(p_e);
        }
    }
}
