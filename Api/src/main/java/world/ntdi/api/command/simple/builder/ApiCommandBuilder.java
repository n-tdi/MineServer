package world.ntdi.api.command.simple.builder;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.bukkit.command.CommandSender;
import world.ntdi.api.command.simple.*;

import java.util.List;

@SuperBuilder
public class ApiCommandBuilder {
    protected Label m_label;
    @Builder.Default
    protected Executor m_nativeExecutor = (CommandSender sender, String[] args) -> true;
    @Builder.Default
    protected Completer m_nativeCompleter = (CommandSender sender, String[] args) -> List.of();
    @Singular
    protected List<ApiCommand> m_subcommands;

    public ApiCommand make() {
        if (m_label == null) {
            throw new IllegalStateException("Label cannot be null");
        }

        final ApiCommand command = new ApiCommand(this.m_label);
        command.setNativeExecutor(this.m_nativeExecutor);
        command.setNativeCompleter(this.m_nativeCompleter);

        for (ApiCommand entry : m_subcommands) {
            command.addSubcommand(entry);
        }

        return command;
    }

    public ApiCommand makeAndRegister() {
        final ApiCommand command = make();
        BukkitBridge.register(new BukkitBridge(command));
        return command;
    }

    public static ApiCommandBuilderBuilder<?, ?> of(Label p_label) {
        return ApiCommandBuilder.builder().label(p_label);
    }

    public static ApiCommandBuilderBuilder<?, ?> of(LabelBuilder p_label) {
        return ApiCommandBuilder.builder().label(p_label.make());
    }
}
