package world.ntdi.api.command.simple;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * This class bridges the gap between CommandCL simple packages and Bukkit command
 * classes.
 * <p>
 * This class is not meant to be used by the end user. It is only used
 * internally by CommandCL to bridge the gap between the simple packages and the
 * Bukkit command classes.
 */
public final class BukkitBridge extends BukkitCommand {
    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    static {
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            COMMAND_MAP_FIELD = field;

            field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            KNOWN_COMMANDS_FIELD = field;

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to get command map from plugin manager");
        }
    }

    private final ApiCommand m_apiCommand;

    public BukkitBridge(ApiCommand p_apiCommand) {
        super(p_apiCommand.getLabel().getName(), p_apiCommand.getLabel().getDescription(), p_apiCommand.getLabel().getUsage(),
                p_apiCommand.getLabel().getAliases());
        this.m_apiCommand = p_apiCommand;
    }

    @Override
    public boolean execute(CommandSender p_sender, String p_commandLabel, String[] p_args) {
        return this.m_apiCommand.execute(p_sender, p_args);
    }

    @Override
    public List<String> tabComplete(CommandSender p_sender, String p_alias, String[] p_args) throws IllegalArgumentException {
        return this.m_apiCommand.complete(p_sender, p_args);
    }

    @SuppressWarnings("unchecked")
    public static void register(@NonNull final BukkitCommand p_command) {
        register(p_command, "api");
    }

    @SuppressWarnings("unchecked")
    public static void register(@NonNull final BukkitCommand p_command, @NonNull final String p_identifier) {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);
            if (cmap.getCommand(p_command.getLabel()) != null) {
                knownCommands.remove(p_command.getLabel());
            }

            p_command.getAliases().forEach((String alias) -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

            cmap.register(p_identifier, p_command);

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void unregister(String p_label) {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);

            final Command command = cmap.getCommand(p_label);

            if (command == null) {
                return;
            }

            knownCommands.remove(p_label);
            command.getAliases().forEach((String alias) -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
