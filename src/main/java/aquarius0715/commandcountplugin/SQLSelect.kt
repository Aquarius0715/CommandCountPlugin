package aquarius0715.commandcountplugin

import aquarius0715.commandcountplugin.CommandCountPlugin.PlayerData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.sql.SQLException
import java.util.*

class SQLSelect(var plugin: CommandCountPlugin) : Thread() {
    @Throws(SQLException::class)
    fun selectScoreBoardStats(player: Player?) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                plugin.MySQLManager.close()
                return
            }

            val sql = ("SELECT scoreBoardStats FROM commandCountTable WHERE StartDate = '" + plugin.StartDate + "' AND UUID = '"
                    + player!!.uniqueId.toString() + "';")
            val resultSet = plugin.MySQLManager.query(sql)
            Objects.requireNonNull(resultSet)!!.next()
            if (Objects.requireNonNull(resultSet)!!.getBoolean("scoreBoardStats")) {
                player.sendMessage("スコアボードを非表示にしました")
                plugin.sqlUpdate.updateScoreBoardStatsFalse(player)
                player.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).newScoreboard
            } else {
                player.sendMessage("スコアボードを表示にしました")
                plugin.sqlUpdate.updateScoreBoardTrue(player)
                player.scoreboard = plugin.scoreboard!!
            }
            plugin.MySQLManager.close()
        }
    }

    @Throws(SQLException::class)
    fun selectPlayerScoreRanking() {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                plugin.MySQLManager.close()
                return
            }
            val sql = "SELECT * FROM commandCountTable WHERE StartDate = '" + plugin.StartDate + "' ORDER BY cmdCount DESC LIMIT 10;"
            val resultSet = plugin.MySQLManager.query(sql)
            plugin.playerData.clear()
            while (resultSet!!.next()) {
                plugin.playerData.add(PlayerData(resultSet.getString("playerName"), resultSet.getInt("cmdCount")))
            }
            val sql1 = "SELECT SUM(cmdCount) from commandCountTable WHERE StartDate = '" + plugin.StartDate + "';"
            val resultSet1 = plugin.MySQLManager.query(sql1)
            resultSet1!!.next()
            plugin.allScore = resultSet1.getInt("SUM(cmdCount)")
            plugin.MySQLManager.close()
        }
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