package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class Timer(var plugin: CommandCountPlugin?) {

    fun CountDown() {
        plugin!!.time = plugin!!.config.getInt("countTime")
        if (!plugin!!.pluginStats) {
            return
        }
        object : BukkitRunnable() {
            override fun run() {
                if (plugin!!.time >= 0) {
                    if (plugin!!.time % 60 == 0 && plugin!!.time != 0) {
                        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + "" + ChatColor.BOLD + "終了まであと" + plugin!!.time / 60 + "分です")
                    } else {
                        if (plugin!!.time == 30) {
                            Bukkit.broadcastMessage(ChatColor.GREEN.toString() + "" + ChatColor.BOLD + "終了まであと" + plugin!!.time + "秒です")
                        } else {
                            if (plugin!!.time <= 10) {
                                Bukkit.broadcastMessage(ChatColor.GREEN.toString() + "" + ChatColor.BOLD + "終了まであと" + plugin!!.time + "秒です")
                            }
                        }
                    }
                } else {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendMessage("レイドが終了しました。")
                        player.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).newScoreboard
                        plugin!!.StartDate = null
                        plugin!!.gameStats = false
                        plugin!!.playerData.clear()
                        plugin!!.time = 0
                        cancel()
                    }
                }
                plugin!!.time--
            }
        }.runTaskTimer(plugin!!, 0, 20)
    }
}