package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class Timer(var plugin: CommandCountPlugin?) {

    fun CountDown() {
        plugin!!.time = (plugin!!.config.getInt("days") * 86400) +
                (plugin!!.config.getInt("hours") * 3600) +
                (plugin!!.config.getInt("minutes") * 60)
        if (!plugin!!.pluginStats) {
            return
        }
        object : BukkitRunnable() {
            override fun run() {
                if (plugin!!.time >= -1) {
                    if (plugin!!.time != 0) {
                        plugin!!.days = plugin!!.time / 86400

                        plugin!!.hours = (plugin!!.time % 86400) / 3600

                        plugin!!.minutes = ((plugin!!.time % 86400) % 3600) / 60

                        plugin!!.seconds = (((plugin!!.time % 86400) % 3600) % 60) / 60
                    }


                    if (plugin!!.time == -1) {
                        for (player in Bukkit.getOnlinePlayers()) {
                            player.sendMessage("レイドが終了しました。")
                            if (player.hasPermission("admin")) {
                                plugin!!.sqlSelect.selectGameResult(player)
                            }
                            player.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).newScoreboard
                            plugin!!.StartDate = null
                            plugin!!.gameStats = false
                            plugin!!.playerData.clear()
                            plugin!!.time = -1
                            cancel()
                        }
                    }
                }
                plugin!!.time--
            }
        }.runTaskTimer(plugin!!, 0, 20)
    }

    fun scoreBoardUpdate() {

        if (!plugin!!.gameStats) {
            return
        }
        object : BukkitRunnable() {
            override fun run() {

                if (!plugin!!.gameStats && plugin!!.time <= -1) {

                    for (player in Bukkit.getOnlinePlayers()) {
                        player.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).newScoreboard
                    }
                    cancel()
                }

                plugin!!.scoreBoardData.updateScoreBoard()
            }
        }.runTaskTimer(plugin!!, 0, plugin!!.config.getLong("updateTime"))
    }
}