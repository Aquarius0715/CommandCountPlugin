package aquarius0715.commandcountplugin

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class Events(var plugin: CommandCountPlugin) : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (plugin.gameStats) {
            event.player.sendMessage(plugin.prefix + "只今レイドが行われています。")
            plugin.scoreBoardData.updateScoreBoard()
            event.player.scoreboard = plugin.scoreboard!!
        }
    }
}