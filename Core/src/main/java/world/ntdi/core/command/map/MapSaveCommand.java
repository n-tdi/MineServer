package world.ntdi.core.command.map;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import world.ntdi.api.command.simple.ApiCommand;
import world.ntdi.api.command.simple.Label;
import world.ntdi.core.map.MapService;

public class MapSaveCommand extends ApiCommand {
    private final MapService m_mapService;
    public MapSaveCommand(MapService p_mapService) {
        super(new Label("save", "kaboom.save"));

        m_mapService = p_mapService;
    }

    @Override
    public boolean execute(@NonNull CommandSender p_commandSender, @NonNull String[] p_args) {
        if (!p_commandSender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(p_commandSender);
            return true;
        };

        m_mapService.snapshotMap();

        return super.execute(p_commandSender, p_args);
    }
}
