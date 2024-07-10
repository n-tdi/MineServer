package world.ntdi.api.command.simple.builder;

import lombok.Singular;
import lombok.experimental.SuperBuilder;
import world.ntdi.api.command.simple.Label;

import java.util.List;

@SuperBuilder
public class LabelBuilder {
    protected String m_name;
    protected String m_permission;
    protected String m_description;
    protected String m_usage;
    @Singular
    protected List<String> aliases;

    public Label make() {
        final Label label = new Label(m_name, m_permission, aliases);
        if (m_usage != null) {
            label.setUsage(m_usage);
        }

        if (m_description != null) {
            label.setDescription(m_description);
        }

        return label;
    }

    public static LabelBuilderBuilder<?, ?> of(String name) {
        return LabelBuilder.builder().name(name);
    }

    public static LabelBuilderBuilder<?, ?> of(String name, String permission) {
        return LabelBuilder.builder().name(name).permission(permission);
    }
}
