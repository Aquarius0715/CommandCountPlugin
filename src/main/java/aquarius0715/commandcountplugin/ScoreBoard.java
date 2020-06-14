package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

public class ScoreBoard {

    CommandCountPlugin plugin;


    public ScoreBoard(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }


    public void createScoreBoard() throws SQLException {
        plugin.scoreboardManager = Bukkit.getScoreboardManager();
        plugin.scoreboard = requireNonNull(plugin.scoreboardManager).getNewScoreboard();
        plugin.objective = plugin.scoreboard.registerNewObjective("cmdCount", "Dummy");
        plugin.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        plugin.objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "レイドスコアボード");

        plugin.team.add(plugin.scoreboard.registerNewTeam("first"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("second"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("third"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("fourth"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("fifth"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("sixth"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("seventh"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("eighth"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("ninth"));
        plugin.team.add(plugin.scoreboard.registerNewTeam("tenth"));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            plugin.sqlSelect.selectPlayerScore(player1);
            player1.setScoreboard(plugin.scoreboard);
        }
    }


    public void updateScoreBoardTime() {

        new BukkitRunnable() {
            @Override
            public void run() {

                if (plugin.time == 0) {
                    cancel();
                }

                try {
                    plugin.sqlSelect.selectPlayerScoreRanking();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                plugin.scoreBoardData.updateScoreBoard();
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
