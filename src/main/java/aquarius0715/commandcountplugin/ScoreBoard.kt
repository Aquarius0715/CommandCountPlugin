package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.DisplaySlot
import java.util.*

class ScoreBoard(var plugin: CommandCountPlugin) {
    fun createScoreBoard() {
        plugin.scoreboardManager = Bukkit.getScoreboardManager()
        plugin.scoreboard = Objects.requireNonNull(plugin.scoreboardManager)!!.newScoreboard
        plugin.objective = plugin.scoreboard!!.registerNewObjective("cmdCount", "Dummy")
        plugin.objective!!.displaySlot = DisplaySlot.SIDEBAR
        plugin.objective!!.displayName = ChatColor.AQUA.toString() + "" + ChatColor.BOLD + "レイドスコアボード"
        for (player1 in Bukkit.getOnlinePlayers()) {
            player1.scoreboard = plugin.scoreboard!!
        }
    }

}