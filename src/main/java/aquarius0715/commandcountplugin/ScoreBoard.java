package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import static java.util.Objects.requireNonNull;

public class ScoreBoard {

    CommandCountPlugin plugin;


    public ScoreBoard(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }


    public void createScoreBoard() {
        plugin.scoreboardManager = Bukkit.getScoreboardManager();
        plugin.scoreboard = requireNonNull(plugin.scoreboardManager).getNewScoreboard();
        plugin.objective = plugin.scoreboard.registerNewObjective("cmdCount", "Dummy");
        plugin.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        plugin.objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "レイドスコアボード");

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.setScoreboard(plugin.scoreboard);
        }
    }
}
