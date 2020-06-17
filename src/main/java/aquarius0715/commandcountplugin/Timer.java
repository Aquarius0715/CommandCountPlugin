package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static java.util.Objects.requireNonNull;

public class Timer {

    CommandCountPlugin plugin;

    public Timer(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void Timer() {
        plugin.time = plugin.getConfig().getInt("countTime");

        if (!plugin.pluginStats) {
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {


                if (plugin.time >= 0) {
                    if (plugin.time % 60 == 0 && plugin.time != 0) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + plugin.time / 60 + "分です");
                    } else {
                        if (plugin.time == 30) {
                            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + plugin.time + "秒です");
                        } else {
                            if (plugin.time <= 10) {
                                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + plugin.time + "秒です");
                            }
                        }
                    }
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("レイドが終了しました。");
                        player.setScoreboard(requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
                        plugin.StartDate = null;
                        plugin.gameStats = false;
                        plugin.playerData.clear();
                        plugin.time = 0;

                        cancel();
                    }
                }
                plugin.time--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
