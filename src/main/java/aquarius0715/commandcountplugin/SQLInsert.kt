package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player

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
            Bukkit.broadcastMessage("DB接続に失敗したためプラグインを停止します。")
            plugin.pluginStats = false
            return false
        }
        return true
    }

}