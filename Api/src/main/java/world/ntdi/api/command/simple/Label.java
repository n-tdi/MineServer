package world.ntdi.api.command.simple;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Label represents a command label.
 * <p>
 * A command label tells the server and client how to identify the command the
 * label is associated with. Every executor must be associated with a label.
 * otherwise the server will have no way to know when to execute the command
 * code.
 */
@Getter
public  class Label {

    private static final String DEFAULT_DESCRIPTION = "Default Command Description Here, oh wait..";

    private final String m_name;
    private final String m_permission;
    @Setter
    private String m_description;
    @Setter
    private String m_usage;
    private final List<String> m_aliases;

    public Label(String p_name, String p_permission, String p_description, List<String> p_aliases) {
        this.m_name = p_name;
        this.m_permission = p_permission;
        this.m_description = p_description;
        this.m_usage = "/" + p_name;
        this.m_aliases = new ArrayList<>(p_aliases);
    }

    public Label(String p_name, String p_permission, String... p_aliases) {
        this(p_name, p_permission, DEFAULT_DESCRIPTION, Arrays.asList(p_aliases));
    }

    public Label(String p_name, String p_permission, List<String> p_aliases) {
        this(p_name, p_permission, DEFAULT_DESCRIPTION, p_aliases);
    }

    public Label(String p_name, String p_permission) {
        this(p_name, p_permission, DEFAULT_DESCRIPTION, new ArrayList<>());
    }

    public void addAlias(String p_alias) {
        m_aliases.add(p_alias);
    }

    public void addAliases(String... p_aliases) {
        this.m_aliases.addAll(Arrays.asList(p_aliases));
    }

}
