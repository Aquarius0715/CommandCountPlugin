package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.sql.SQLException

class Commands(var plugin: CommandCountPlugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (label.equals("cmdcount", ignoreCase = true)) {
            if (sender !is Player) {
                sender.sendMessage("You cannot this")
                return true
            }
            if (args.size == 0) {
                if (plugin.pluginStats) {
                    sender.sendMessage("CommandCountPluginは有効です。")
                    return true
                } else sender.sendMessage("CommandCountPluginは無効です")
                return true
            }
        }
        if (args.size == 1) {
            if (args[0].equals("help", ignoreCase = true)) {
                return if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています")
                    false
                } else {
                    sender.sendMessage("cmdCounter")
                    //TODO Write help text
                    true
                }
            }

            //未実装
            if (args[0].equals("reset", ignoreCase = true)) {
                return if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています")
                    false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。")
                        false
                    } else {
                        if (!sender.hasPermission("admin")) {
                            sender.sendMessage("あなたはこのコマンドを使用することができません")
                            false
                        } else {
                            plugin.sqlUpdate.resetScore()
                            sender.sendMessage("リセットしました。")
                            true
                        }
                    }
                }
            }
            if (args[0].equals("start", ignoreCase = true)) {
                return if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。")
                    false
                } else {
                    if (plugin.gameStats) {
                        sender.sendMessage("レイドは既に始まっています。")
                        false
                    } else {
                        if (!sender.hasPermission("admin")) {
                            sender.sendMessage("あなたはこのコマンドを使用することができません")
                            false
                        } else {
                            plugin.gameStats = true
                            plugin.dateFormant.StartTime()
                            for (player in Bukkit.getOnlinePlayers()) {
                                plugin.sqlInsert.insertDefaultTable(player) //query
                            }
                            plugin.scoreBoard.createScoreBoard()
                            plugin.timer.CountDown()
                            try {
                                plugin.sqlSelect.selectPlayerScoreRanking() //query
                            } catch (e: SQLException) {
                                e.printStackTrace()
                            }
                            plugin.scoreBoardData.updateScoreBoard()
                            sender.sendMessage("レイドが始まりました。")
                            true
                        }
                    }
                }
            }
            if (args[0].equals("scoreboard", ignoreCase = true)) {
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。")
                    return false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。")
                        return false
                    }
                }
                try {
                    plugin.sqlSelect.selectScoreBoardStats((sender as Player).player)
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
                return true
            }
            if (args[0].equals("add", ignoreCase = true)) {
                val player = sender as Player
                return if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。")
                    false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。")
                        return false
                    }
                    try {
                        plugin.sqlUpdate.updateScore(player) //query
                        plugin.sqlSelect.selectPlayerScoreRanking() //query
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                    plugin.scoreBoardData.updateScoreBoard()
                    true
                }
            }
            if (args[0].equals("on", ignoreCase = true)) {
                if (!plugin.pluginStats) {
                    plugin.pluginStats = true
                    sender.sendMessage("プラグインを有効にしました。")
                    return true
                } else sender.sendMessage("プラグインはすでに有効です。")
            }
            if (args[0].equals("off", ignoreCase = true)) {
                if (!plugin.pluginStats) {
                    plugin.pluginStats = false
                    sender.sendMessage("プラグインを無効にしました")
                    return true
                } else sender.sendMessage("プラグインはすでに無効です。")
            }
            if (args[0].equals("joinsettings", ignoreCase = true)) {
                if (!sender.hasPermission("admin")) {
                    sender.sendMessage("あなたはこのコマンドを使用することができません。")
                    return false
                }
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインは無効になっています。")
                    return false
                }
                if (plugin.joinOnTheWay) {
                    sender.sendMessage("途中参加を許可しました。")
                    plugin.joinOnTheWay = false
                    return false
                } else sender.sendMessage("途中参加を禁止にしました。")
                plugin.joinOnTheWay = true
                return true
            }
        }
        return false
    }

}