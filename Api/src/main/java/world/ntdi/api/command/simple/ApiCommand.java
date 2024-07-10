package world.ntdi.api.command.simple;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
// not in a multi-threaded environment for commands nor woudl that be wanted
@SuppressWarnings("java:S3366")
public class ApiCommand implements Completer, Executor {
    @Getter
    private final Label m_label;
    @Setter
    private Executor m_nativeExecutor;
    @Setter
    private Completer m_nativeCompleter;
    private final Map<String, ApiCommand> m_subcommands;

    public ApiCommand(@NonNull Label p_label) {
        this.m_label = p_label;
        this.m_nativeExecutor = null;
        this.m_nativeCompleter = null;
        this.m_subcommands = new HashMap<>();
    }

    @Override
    public boolean execute(@NonNull CommandSender p_commandSender, @NonNull String[] p_args) {
        if (p_args.length == 0) {
            boolean result = true;
            if (this.m_nativeExecutor != null) {
                result = this.m_nativeExecutor.execute(p_commandSender, p_args);
            }
            return result;
        }

        final ApiCommand sub = m_subcommands.getOrDefault(p_args[0], null);
        if (sub == null) {
            return true;
        }

        final String[] subArgs = new String[p_args.length - 1];
        System.arraycopy(p_args, 1, subArgs, 0, subArgs.length);
        return sub.execute(p_commandSender, subArgs);
    }

    @Override
    public List<String> complete(CommandSender p_commandSender, String[] p_args) {

        if (m_nativeCompleter != null) {
            return m_nativeCompleter.complete(p_commandSender, p_args);
        }

        if (p_args.length == 1) {
            return this.m_subcommands.keySet().stream().filter((String s) -> {
                final ApiCommand sub = m_subcommands.get(s);
                if (sub.getLabel().getPermission() == null) {
                    return true;
                }

                return p_commandSender.hasPermission(sub.getLabel().getPermission());
            }).toList();
        }

        final ApiCommand sub = m_subcommands.getOrDefault(p_args[0], null);
        if (sub == null) {
            return List.of();
        }

        if (sub.getLabel().getPermission() != null && !p_commandSender.hasPermission(sub.getLabel().getPermission())) {
            return List.of();
        }

        final String[] subArgs = new String[p_args.length - 1];
        System.arraycopy(p_args, 1, subArgs, 0, subArgs.length);
        return sub.complete(p_commandSender, subArgs);
    }

    public void addSubcommand(@NonNull ApiCommand p_subcommand) {

        String name = this.findOverlappingSubcommand(p_subcommand.getLabel().getName());
        if (name != null) {
            throw new IllegalArgumentException("Subcommand '" + name + "' already exists");
        }

        name = this.findOverlappingSubcommand(p_subcommand.getLabel().getAliases().toArray(String[]::new));
        if (name != null) {
            throw new IllegalArgumentException("Subcommand with alias '" + name + "' already exists for subcommand '"
                    + p_subcommand.getLabel().getName() + "'");
        }

        this.m_subcommands.put(p_subcommand.getLabel().getName(), p_subcommand);
        for (String alias : p_subcommand.getLabel().getAliases()) {
            this.m_subcommands.put(alias, p_subcommand);
        }
    }

    public void removeSubcommand(@NonNull final String p_name) {
        if (this.m_subcommands.containsKey(p_name)) {
            ApiCommand subcommand = this.m_subcommands.get(p_name);
            this.m_subcommands.remove(p_name);
            for (String alias : subcommand.getLabel().getAliases()) {
                this.m_subcommands.remove(alias);
            }
        }
    }

    private String findOverlappingSubcommand(@NonNull final String... p_aliases) {
        for (String alias : p_aliases) {
            if (this.m_subcommands.containsKey(alias)) {
                return alias;
            }
        }
        return null;
    }

    protected void sendNoPermission(CommandSender p_commandSender) {
        p_commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', getLabel().getPermission()));
    }

    @SuppressWarnings("All")
    protected void sendIncorrectUsage(CommandSender p_sender, String p_correctUsage) {
        p_sender.sendMessage(ChatColor.translateAlternateColorCodes('&', p_correctUsage));
    }

    protected void sendIncorrectUsage(CommandSender p_sender) {
        sendIncorrectUsage(p_sender, "");
    }


}
