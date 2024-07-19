package world.ntdi.api.sql.entity;

import com.j256.ormlite.dao.ForeignCollection;
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
@DatabaseTable(tableName = "group")
public class GroupEntity {
    @DatabaseField(generatedId = true, columnName = "id")
    private int m_id;

    @DatabaseField(columnName = "name", canBeNull = false)
    private String m_name;

    @DatabaseField(columnName = "prefix", canBeNull = false)
    private String m_prefix;

    @DatabaseField(columnName = "permissions")
    private ForeignCollection<GroupPermissionEntity> m_groupPermissionEntities;
}
