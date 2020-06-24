package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.sql.SQLException

class SQLUpdate(var plugin: CommandCountPlugin) : Thread() {

    @Throws(SQLException::class)
    fun updateScore(player: Player) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }

            val sql = ("SELECT cmdCount FROM commandCountTable WHERE StartDate = '"
                    + plugin.StartDate + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            val resultSet = plugin.MySQLManager.query(sql)
            resultSet!!.next()
            val score = resultSet.getInt("cmdCount") + plugin.config.getInt("addScore")
            val sql1 = ("UPDATE commandCountTable set cmdCount = "
                    + score
                    + " WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            plugin.MySQLManager.execute(sql1)
            player.sendMessage(plugin.prefix + ChatColor.GRAY.toString() + "スコアが" +
                    plugin.config.getInt("addScore") + "増えました。")
        }
    }

    fun updateScoreAdmin(player: Player, score: Int) {
        Bukkit.getScheduler().runTask(plugin, this)

        run {
            if (!sqlConnectSafely()) {
                return
            }

            val sql1 = ("UPDATE commandCountTable set cmdCount = "
                    + plugin.addScoreOp
                    + " WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            plugin.MySQLManager.execute(sql1)
            player.sendMessage(plugin.prefix + ChatColor.GRAY.toString() + "スコアを" + score + "に設定しました。")
            plugin.scoreBoardData.updateScoreBoard()
        }

    }

    fun resetScore() {
        Bukkit.getScheduler().runTask(plugin, this)
        if (!sqlConnectSafely()) {
            return
        }
        val sql = "UPDATE commandCountTable SET cmdCount = 0 WHERE StartDate = '" + plugin.StartDate + "';"
        plugin.MySQLManager.execute(sql)
        plugin.playerData.clear()
        plugin.allScore = 0
        plugin.scoreBoardData.updateScoreBoard()
    }

    fun sqlConnectSafely(): Boolean {
        if (!plugin.MySQLManager.connectCheck()) {
            Bukkit.broadcastMessage("DB接続に失敗したためプラグインを停止します。")
            plugin.pluginStats = false
            plugin.gameStats = false
            return false
        }
        return true
    }

}