package world.ntdi.api.command;

import lombok.NonNull;
import world.ntdi.api.command.simple.ApiCommand;
import world.ntdi.api.command.simple.BukkitBridge;
import world.ntdi.api.command.simple.Label;
import world.ntdi.api.command.simple.builder.ApiCommandBuilder;
import world.ntdi.api.command.simple.builder.LabelBuilder;

public final class CommandCL {
    private CommandCL() {}

    public static void register(@NonNull final ApiCommand p_apiCommand) {
        BukkitBridge.register(new BukkitBridge(p_apiCommand));
    }

    public static void register(@NonNull final ApiCommand p_apiCommand, @NonNull final String p_identifier) {
        BukkitBridge.register(new BukkitBridge(p_apiCommand), p_identifier);
    }

    public static void unregister(@NonNull final String p_commandName) {
        BukkitBridge.unregister(p_commandName);
    }

    @SuppressWarnings("unchecked")
    public static <C extends ApiCommandBuilder, B extends ApiCommandBuilder.ApiCommandBuilderBuilder<C, B>> ApiCommandBuilder.ApiCommandBuilderBuilder<C, B> command(
            @NonNull final Label p_label) {
        return (ApiCommandBuilder.ApiCommandBuilderBuilder<C, B>) ApiCommandBuilder.of(p_label);
    }

    @SuppressWarnings("unchecked")
    public static <C extends ApiCommandBuilder, B extends ApiCommandBuilder.ApiCommandBuilderBuilder<C, B>> ApiCommandBuilder.ApiCommandBuilderBuilder<C, B> command(
            @NonNull final LabelBuilder p_label) {
        return (ApiCommandBuilder.ApiCommandBuilderBuilder<C, B>) ApiCommandBuilder.of(p_label);
    }

    @SuppressWarnings("unchecked")
    public static <C extends LabelBuilder, B extends LabelBuilder.LabelBuilderBuilder<C, B>> LabelBuilder.LabelBuilderBuilder<C, B> label(
            @NonNull final String p_name) {
        return (LabelBuilder.LabelBuilderBuilder<C, B>) LabelBuilder.of(p_name);
    }

    @SuppressWarnings("unchecked")
    public static <C extends LabelBuilder, B extends LabelBuilder.LabelBuilderBuilder<C, B>> LabelBuilder.LabelBuilderBuilder<C, B> label(
            @NonNull final String p_name, @NonNull final String p_permission) {
        return (LabelBuilder.LabelBuilderBuilder<C, B>) LabelBuilder.of(p_name, p_permission);
    }
}
