package world.ntdi.api.sql.service.services;

import com.j256.ormlite.dao.Dao;
import world.ntdi.api.sql.entity.GroupEntity;

import java.util.List;

public interface GroupService {
    Dao<GroupEntity, Integer> getGroupDao();

    GroupEntity getGroupOrDefault(String p_name);
    GroupEntity addPermission(GroupEntity p_groupEntity, String p_permission);
    void saveGroup(GroupEntity p_groupEntity);
    void deleteGroup(GroupEntity p_groupEntity);
    void deletePermission(GroupEntity p_groupEntity, String p_permission);
    List<String> getAllPermissions(GroupEntity p_groupEntity);
    List<GroupEntity> getAllGroups();
}
