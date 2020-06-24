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
        plugin.objective!!.displayName = plugin.prefix
        for (player1 in Bukkit.getOnlinePlayers()) {
            player1.scoreboard = plugin.scoreboard!!
        }
    }

}