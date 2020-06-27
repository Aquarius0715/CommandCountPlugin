package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class SQLInsert(var plugin: CommandCountPlugin) : Thread() {
    fun insertDefaultTable(player: Player) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }
            val sql = ("INSERT INTO commandCountTable (StartDate, UUID, playerName, cmdCount, scoreBoardStats) VALUE(" + "'"
                    + plugin.StartDate + "' , '"
                    + player.uniqueId.toString() + "' , '"
                    + player.displayName + "' , "
                    + 0 + " , "
                    + true + ");")
            plugin.MySQLManager.execute(sql)
        }
    }

    fun sqlConnectSafely(): Boolean {
        if (!plugin.MySQLManager.connectCheck()) {
            Bukkit.broadcastMessage(plugin.prefix + "DB接続に失敗したためプラグインを停止します。")
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage("レイドが終了しました。")
                if (player.hasPermission("admin")) {
                    plugin.sqlSelect.selectGameResult(player)
                }
                player.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).newScoreboard
                plugin.StartDate = null
                plugin.gameStats = false
                plugin.playerData.clear()
                plugin.time = -1
                plugin.MySQLManager.close()
            }
            return false
        }
        return true
    }

}