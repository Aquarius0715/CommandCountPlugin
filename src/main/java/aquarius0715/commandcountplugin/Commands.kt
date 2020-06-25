package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.sql.SQLException
import java.util.*

class Commands(var plugin: CommandCountPlugin) : CommandExecutor {

    var cmdDate: Long = 0
    var nowDate: Long = 0

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (label.equals("cmdcount", ignoreCase = true)) {
            if (sender !is Player) {
                sender.sendMessage(plugin.prefix + "You cannot this")
                return true
            }
            if (args.isEmpty()) {
                if (plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "CommandCountPluginは有効です。")
                    return true
                } else sender.sendMessage(plugin.prefix + "CommandCountPluginは無効です")
                return true
            }
        }
        if (args.size == 1) {
            if (args[0].equals("help", ignoreCase = true)) {
                return if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています")
                    false
                } else {
                    if (sender.hasPermission("admin")) {
                        sender.sendMessage(plugin.prefix + "</cmdcount reset> : スコアをリセットします。")
                        sender.sendMessage(plugin.prefix + "</cmdcount on> : プラグインをオンにします。")
                        sender.sendMessage(plugin.prefix + "</cmdcount off> : プラグインをオフにします。")
                        sender.sendMessage(plugin.prefix + "</cmdcount setscore [数]> : 指定されたスコアに設定します。")
                        sender.sendMessage(plugin.prefix + "</cmdcount start> : レイドをスタートします。")
                        sender.sendMessage(plugin.prefix + "</cmdcount stop> : レイドを途中停止させます。")
                        sender.sendMessage(plugin.prefix + "</cmdcount settime [日][時間][分]> : 制限時間を指定された時間に設定します。")
                        sender.sendMessage(plugin.prefix + "</cmdcount add> : スコアを1加えます。")
                    }
                    true
                }
            }

            if (args[0].equals("reset", ignoreCase = true)) {

                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません")
                    return false
                }

                return if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています")
                    false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage(plugin.prefix + "レイドはまだ始まっていません。")
                        false
                    } else {
                        plugin.sqlUpdate.resetScore()
                            sender.sendMessage(plugin.prefix + "リセットしました。")
                            true
                        }
                    }
                }

            if (args[0].equals("start", ignoreCase = true)) {

                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません。")
                    return false
                }

                return if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています。")
                    false
                } else {
                    if (plugin.gameStats) {
                        sender.sendMessage(plugin.prefix + "レイドは既に始まっています。")
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
                        plugin.timer.scoreBoardUpdate()
                        sender.sendMessage(plugin.prefix + "レイドが始まりました。")
                        true
                    }
                }
            }


            if (args[0].equals("stop", ignoreCase = true)) {

                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません")
                    return false
                }

                return if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています。")
                    false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage(plugin.prefix + "レイドは既に停止しています。")
                        false
                    } else {

                            for (player in Bukkit.getOnlinePlayers()) {
                                player.sendMessage(plugin.prefix + "レイドが強制終了しました。")
                                plugin.time = -1
                            }
                            true
                        }
                    }
                }

            if (args[0].equals("add", ignoreCase = true)) {
                val player = sender as Player
                nowDate = Date().time
                plugin.playerData.clear()

                if (nowDate - cmdDate < plugin.config.getInt("coolTime") * 1000) {
                    sender.sendMessage(plugin.prefix + "クールダウン中です。")
                    return false
                }

                return if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています。")
                    false
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage(plugin.prefix + "レイドはまだ始まっていません。")
                        return false
                    }
                    try {
                        plugin.sqlUpdate.updateScore(player) //query
                        plugin.sqlSelect.selectPlayerScoreRanking() //query
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }

                    cmdDate = Date().time

                    return true
                }
            }

            if (args[0].equals("on", ignoreCase = true)) {

                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません。")
                    return false
                }
                if (!plugin.pluginStats) {
                    plugin.pluginStats = true
                    sender.sendMessage(plugin.prefix + "プラグインを有効にしました。")
                    return true
                } else sender.sendMessage(plugin.prefix + "プラグインはすでに有効です。")
            }

            if (args[0].equals("off", ignoreCase = true)) {
                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません。")
                    return false
                }

                if (plugin.pluginStats) {
                    plugin.pluginStats = false
                    sender.sendMessage(plugin.prefix + "プラグインを無効にしました")
                    return true
                } else sender.sendMessage(plugin.prefix + "プラグインはすでに無効です。")
            }
        }

        if (args.size == 2) {
            if (args[0].equals("setscore", ignoreCase = true)) {

                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません。")
                    return false
                }
                if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインは無効になっています。")
                    return false
                }
                if (!plugin.gameStats) {
                    sender.sendMessage(plugin.prefix + "レイドはまだ始まっていません。")
                    return false
                } else {
                    try {
                        plugin.addScoreOp = args[1].toInt()
                    } catch (e: NumberFormatException) {
                        sender.sendMessage(plugin.prefix + " 設定する数を入力してください。")
                        return false
                    }
                    plugin.sqlUpdate.updateScoreAdmin(sender as Player, plugin.addScoreOp)
                    plugin.scoreBoardData.updateScoreBoard()
                    return true
                }
            }
        }

        if (args.size == 4) {

            if (args[0].equals("settime", ignoreCase = true)) {
                if (!sender.hasPermission("admin")) {
                    sender.sendMessage(plugin.prefix + "あなたはこのコマンドを使用することができません。")
                    return false
                }
                if (!plugin.pluginStats) {
                    sender.sendMessage(plugin.prefix + "プラグインが無効になっています。")
                    return false
                } else {
                    try {
                        args[1].toInt()
                        args[2].toInt()
                        args[3].toInt()
                    } catch (e: java.lang.NumberFormatException) {
                        sender.sendMessage(plugin.prefix + "設定する数を入力してください。")
                        return false
                    }
                    plugin.config.set("days", args[1].toInt())
                    plugin.config.set("hours", args[2].toInt())
                    plugin.config.set("minutes", args[3].toInt())
                    plugin.config.save("config.yml")
                    sender.sendMessage(plugin.prefix + "レイド時間が"
                            + args[1].toInt() + "日"
                            + args[2].toInt() + "時間"
                            + args[3].toInt() + "分に設定されました。")
                    return true
                }
            }
        }
        return false
    }
}