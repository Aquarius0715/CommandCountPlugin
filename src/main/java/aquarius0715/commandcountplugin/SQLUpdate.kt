package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.sql.SQLException

class SQLUpdate(var plugin: CommandCountPlugin) : Thread() {
    fun updateScoreBoardStatsFalse(player: Player?) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }
            val sql = ("UPDATE commandCountTable SET scoreBoardStats = false " +
                    "WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player!!.uniqueId.toString() + "';")
            plugin.MySQLManager.execute(sql)
        }
    }

    fun updateScoreBoardTrue(player: Player?) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }
            val sql = ("UPDATE commandCountTable SET scoreBoardStats = true " +
                    "WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player!!.uniqueId.toString() + "';")
            plugin.MySQLManager.execute(sql)
        }
    }

    //未実装
    @Throws(SQLException::class)
    fun updateScore(player: Player) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }
            if (!plugin.joinOnTheWay) {
                player.sendMessage("途中参加は許可されていません。")
                return
            }
            val sql = ("SELECT cmdCount FROM commandCountTable WHERE StartDate = '"
                    + plugin.StartDate + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            val resultSet = plugin.MySQLManager.query(sql)
            resultSet!!.next()
            val score = resultSet.getInt("cmdCount") + 1
            val sql1 = ("UPDATE commandCountTable set cmdCount = "
                    + score
                    + " WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            plugin.MySQLManager.execute(sql1)
            player.sendMessage(ChatColor.GRAY.toString() + "スコアが1増えました。")
        }
    }

    fun resetScore() {
        if (!sqlConnectSafely()) {
            return
        }
        val sql = "UPDATE commandCountTable SET cmdCount = 0 WHERE StartDate = '';"
        plugin.MySQLManager.execute(sql)
        plugin.playerData.clear()
        plugin.scoreBoardData.updateScoreBoard()
    }

    fun sqlConnectSafely(): Boolean {
        if (!plugin.MySQLManager.connectCheck()) {
            Bukkit.broadcastMessage("DB接続に失敗したためプラグインを停止します。")
            plugin.pluginStats = false
            return false
        }
        return true
    }

}