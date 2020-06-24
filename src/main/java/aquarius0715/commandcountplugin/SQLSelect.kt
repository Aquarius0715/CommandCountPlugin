package aquarius0715.commandcountplugin

import aquarius0715.commandcountplugin.CommandCountPlugin.PlayerData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.sql.SQLException

class SQLSelect(var plugin: CommandCountPlugin) : Thread() {

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

    fun selectGameResult(player: Player) {
        Bukkit.getScheduler().runTask(plugin, this)
        run {
            if (!sqlConnectSafely()) {
                return
            }
            player.sendMessage("データを確認しています...")

            val sql = "SELECT * FROM commandCountTable WHERE StartDate = '" + plugin.StartDate + "';"
            val resultSet = plugin.MySQLManager.query(sql)
            if (resultSet == null) {
                player.sendMessage("データベースに情報が登録されていません、エラーが発生しました。")
                return
            }
                player.sendMessage("データベースに情報が正常に登録されています。レイドを終了します。")
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