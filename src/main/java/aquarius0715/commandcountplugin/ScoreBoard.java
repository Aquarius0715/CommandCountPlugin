package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.sql.SQLException;
import java.util.Date;

import static java.util.Objects.requireNonNull;

public class ScoreBoard {

    CommandCountPlugin plugin;
    int time = 0;

    public ScoreBoard(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }


    public void createScoreBoard() throws SQLException {
        plugin.scoreboardManager = Bukkit.getScoreboardManager();
        plugin.scoreboard = requireNonNull(plugin.scoreboardManager).getNewScoreboard();
        plugin.objective = plugin.scoreboard.registerNewObjective("cmdCount", "Dummy");
        plugin.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        plugin.objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "レイドスコアボード");

        plugin.first = plugin.scoreboard.registerNewTeam("first");
        plugin.second = plugin.scoreboard.registerNewTeam("second");
        plugin.third = plugin.scoreboard.registerNewTeam("third");
        plugin.fourth = plugin.scoreboard.registerNewTeam("fourth");
        plugin.fifth = plugin.scoreboard.registerNewTeam("fifth");
        plugin.sixth = plugin.scoreboard.registerNewTeam("sixth");
        plugin.seventh = plugin.scoreboard.registerNewTeam("seventh");
        plugin.eighth = plugin.scoreboard.registerNewTeam("eighth");
        plugin.ninth = plugin.scoreboard.registerNewTeam("ninth");
        plugin.tenth = plugin.scoreboard.registerNewTeam("tenth");

        for (Player player : Bukkit.getOnlinePlayers()) {
            plugin.sqlSelect.selectPlayerScore(player);
        }
        plugin.scoreBoardData.addScoreBoard();
    }


    public void Timer() {
        time = plugin.getConfig().getInt("countTime");

        if (!plugin.pluginStats) {
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {


                if (time >= 0) {
                    if (time % 60 == 0 && time != 0) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + time / 60 + "分です");
                    } else {
                        if (time == 30) {
                            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + time + "秒です");
                        } else {
                            if (time <= 10) {
                                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + time + "秒です");
                            }
                        }
                    }
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("レイドが終了しました。");
                        player.setScoreboard(requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
                        plugin.StartDate = null;
                        plugin.gameStats = false;
                        plugin.rankingDisplayName = null;
                        plugin.rankingScore = null;
                        cancel();
                    }
                }
                plugin.finishDate = new Date();
                time--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void updateTime() {

        new BukkitRunnable() {
            @Override
            public void run() {

                if (time == 0) {
                    cancel();
                }
                try {
                    plugin.sqlSelect.selectPlayerScoreRanking();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                plugin.scoreBoardData.addScoreBoard();
            }
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("updateTime"));
    }
}
