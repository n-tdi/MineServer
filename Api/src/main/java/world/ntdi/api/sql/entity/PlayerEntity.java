package world.ntdi.api.sql.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import world.ntdi.api.pet.Pets;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "player_stats")
public class PlayerEntity {
    @DatabaseField(id = true, canBeNull = false, unique = true, columnName = "uuid")
    private UUID m_uuid;

    @DatabaseField(columnName = "currency", canBeNull = false)
    private float m_currency;

    @DatabaseField(columnName = "experience", canBeNull = false)
    private int m_experience;

    @DatabaseField(columnName = "pet_slot_1", canBeNull = true)
    private Pets m_petSlot1;

    @DatabaseField(columnName = "pet_slot_2", canBeNull = true)
    private Pets m_petSlot2;

    @DatabaseField(columnName = "pet_slot_3", canBeNull = true)
    private Pets m_petSlot3;

    @DatabaseField(columnName = "permissions")
    private ForeignCollection<PlayerPermissionEntity> m_playerPermissionEntities;

    @DatabaseField(columnName = "prefix", canBeNull = true)
    private String m_prefix;

    @DatabaseField(columnName = "group", foreign = true, foreignAutoRefresh = true)
    private GroupEntity m_groupEntity;



    private int getLevel() {
        return (int) Math.floor((double) getExperience() / 100); // TODO: Change 100 to yk, wtv
    }
}
