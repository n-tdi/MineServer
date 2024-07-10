package world.ntdi.api.command.simple;

import lombok.NonNull;
import org.bukkit.command.CommandSender;

/**
 * A Functional Interface that represents an executor for a Command. This
 * is different from the Bukkit executor "CommandExecutor" which contains a
 * String label. This is prone to be abused by beginners who tend to not
 * separate their code.
 */
@FunctionalInterface
public interface Executor {
    /**
     * Acts as an executor for a Command.
     *
     * @param p_commandSender the sender of the command
     * @param p_args   all arguments associated with the command
     * @return true if the command was successfully executed otherwise false
     */
    boolean execute(@NonNull final CommandSender p_commandSender, @NonNull final String[] p_args);
}
