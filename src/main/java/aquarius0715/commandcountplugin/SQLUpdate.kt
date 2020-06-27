package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.sql.SQLException
import java.util.*

class SQLUpdate(var plugin: CommandCountPlugin) : Thread() {

    @Throws(SQLException::class)
    fun updateScore(player: Player) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }

            val sql = ("select cmdCount FROM commandCountTable WHERE StartDate = '"
                    + plugin.StartDate + "' AND UUID = '"
                    + player.uniqueId.toString() + "';")
            var resultSet = plugin.MySQLManager.query(sql)

            if (!resultSet!!.next()) {
                plugin.sqlInsert.insertDefaultTable(player)
                val sql2 = ("select cmdCount FROM commandCountTable WHERE StartDate = '"
                        + plugin.StartDate + "' AND UUID = '"
                        + player.uniqueId.toString() + "';")
                resultSet = plugin.MySQLManager.query(sql2)
                resultSet!!.next()
            }

            val score = resultSet.getInt("cmdCount") + plugin.config.getInt("addScore")
            resultSet.close()
            plugin.MySQLManager.close()

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
        plugin.MySQLManager.close()
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