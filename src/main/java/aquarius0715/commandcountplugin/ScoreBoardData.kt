package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.scoreboard.Score

class ScoreBoardData(var plugin: CommandCountPlugin) {
    fun updateScoreBoard() {
        plugin.scoreBoard.createScoreBoard()
        val score1 = plugin.objective!!.getScore("全体のスコア : " + plugin.allScore)
        score1.score = 10
        for ((count, i) in plugin.playerData.indices.withIndex()) {
            val score: Score = plugin.objective!!.getScore((count + 1).toString() + "位 : " + plugin.playerData[count]!!.playerName + " : " + plugin.playerData[count]!!.score)
            score.score = 9 - count
        }
        for (player in Bukkit.getOnlinePlayers()) {
            player.scoreboard = plugin.scoreboard!!
        }
    }

}



