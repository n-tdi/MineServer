package world.ntdi.core.command.map;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import world.ntdi.api.command.simple.ApiCommand;
import world.ntdi.api.command.simple.builder.LabelBuilder;
import world.ntdi.core.hologram.HologramService;
import world.ntdi.core.map.MapService;

import java.util.List;

public class MapCommand extends ApiCommand {
    public MapCommand(final MapService p_mapService, final HologramService p_hologramService) {
        super(LabelBuilder.of("map", "kaboom.map").build().make());

        addSubcommand(new MapRestoreCommand(p_mapService, p_hologramService));
    }

    @Override
    public boolean execute(@NonNull CommandSender p_commandSender, @NonNull String[] p_args) {
        return super.execute(p_commandSender, p_args);
    }

    @Override
    public List<String> complete(CommandSender p_commandSender, String[] p_args) {

        return super.complete(p_commandSender, p_args);
    }
}
