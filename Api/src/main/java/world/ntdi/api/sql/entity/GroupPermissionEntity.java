package world.ntdi.api.sql.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "group_permission")
public class GroupPermissionEntity {
    @DatabaseField(generatedId = true, columnName = "id")
    private int m_id;

    @DatabaseField(columnName = "permission")
    private String m_permission;

    @DatabaseField(columnName = "group", foreign = true, foreignAutoRefresh = true)
    private GroupEntity m_groupEntity;
}
