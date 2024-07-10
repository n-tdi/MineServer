package world.ntdi.api.command.simple;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A Functional Interface that represents a completer for a Command. This
 * is different from the Bukkit completer "TabCompleter" which contains a String
 * label. This is prone to be abused by beginners who tend to not separate their
 * code.
 */
@FunctionalInterface
public interface Completer {
    /**
     * Acts as a completer for an Command.
     *
     * @param p_commandSender the sender of the command
     * @param p_args   all arguments associated with the command
     * @return a list of possible completions
     */
    List<String> complete(CommandSender p_commandSender, String[] p_args);

    static List<String> empty() {
        return List.of();
    }

    static List<String> intRange(int p_min, int p_max, int p_step) {
        List<String> list = new ArrayList<>();
        for (int i = p_min; i <= p_max; i += p_step) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    static List<String> doubleRange(double p_min, double p_max, double p_step) {
        List<String> list = new ArrayList<>();
        for (double i = p_min; i <= p_max; i += p_step) {
            list.add(String.valueOf(Math.round(i * 10)/10));
        }
        return list;
    }

    static List<String> onlinePlayers() {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }
        return list;
    }

    static List<String> onlinePlayers(String p_startsWith) {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().startsWith(p_startsWith)) {
                list.add(player.getName());
            }
        }
        return list;
    }

}
