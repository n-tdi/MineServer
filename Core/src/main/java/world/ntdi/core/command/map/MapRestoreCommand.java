package world.ntdi.core.command.map;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import world.ntdi.api.command.simple.ApiCommand;
import world.ntdi.api.command.simple.Label;
import world.ntdi.core.map.MapService;

public class MapRestoreCommand extends ApiCommand {
    private final MapService m_mapService;
    public MapRestoreCommand(MapService p_mapService) {
        super(new Label("restore", "kaboom.restore"));

        m_mapService = p_mapService;
    }

    @Override
    public boolean execute(@NonNull CommandSender p_commandSender, @NonNull String[] p_args) {
        if (!p_commandSender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(p_commandSender);
            return true;
        };

        m_mapService.teleportAllPlayersToSpawn();
        m_mapService.restoreMap();

        return super.execute(p_commandSender, p_args);
    }
}
