package world.ntdi.api.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

@Getter
public abstract class ApiScoreboard {
    private final Scoreboard m_scoreboard;
    private final Objective m_objective;
    private final int m_spaceCounter;
    private int m_lineCounter;

    public ApiScoreboard(final String p_title) {
        m_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        m_objective = m_scoreboard.registerNewObjective("statboard", "dummy", p_title);
        m_objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        m_spaceCounter = 1;
        m_lineCounter = 15;

        initialize();
    }

    public void setScoreboard(final Player p_player) {
        p_player.setScoreboard(getScoreboard());
    }

    private Team updateLine(final String p_name) {
        return m_scoreboard.getTeam(p_name);
    }

    private void updateLinePrefix(final String p_name, final String p_prefix) {
        updateLine(p_name).setPrefix(p_prefix);
    }

    private void updateLineSuffix(final String p_name, final String p_suffix) {
        updateLine(p_name).setSuffix(p_suffix);
    }

    private Score newLine(final String p_name, final String p_prefix, final String p_suffix) {
        final String ID = UUID.randomUUID().toString();
        final Team team = m_scoreboard.registerNewTeam(p_name);

        team.addEntry(ID);
        team.setPrefix(p_prefix);
        team.setSuffix(p_suffix);

        return m_objective.getScore(ID);
    }

    public void addLine(final String p_name, final String p_prefix, final String p_suffix) {
        newLine(p_name, p_prefix, p_suffix).setScore(decreaseLineCount());
    }

    public void addBlackLine() {
        m_objective.getScore(new String(new char[m_spaceCounter]).replace('\0', ' ')).setScore(decreaseLineCount());
    }

    private int decreaseLineCount() {
        if (m_lineCounter < 1) {
            throw new IndexOutOfBoundsException("Too many scoreboard lines");
        }

        final int temp = m_lineCounter;
        m_lineCounter -= 1;

        return temp;
    }

    public abstract void initialize();

    public void setDisplayHealthUnderName(final boolean p_value) {
        if (p_value) {
            if (m_scoreboard.getObjective("health") == null) {
                Objective healthObjective = m_scoreboard.registerNewObjective("health", Criteria.HEALTH, ChatColor.RED + "❤", RenderType.INTEGER);
                healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        } else {
            if (m_scoreboard.getObjective("health") != null) {
                Objective health = m_scoreboard.getObjective("health");
                health.unregister();
            }
        }
    }
}