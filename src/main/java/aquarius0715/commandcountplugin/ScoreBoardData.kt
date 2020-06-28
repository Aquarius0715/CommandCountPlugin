package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Score

class ScoreBoardData(var plugin: CommandCountPlugin) {

    private var prefix = ""

    fun updateScoreBoard() {

    plugin.scoreBoard.createScoreBoard()
    val countDown = plugin.objective!!.getScore( ""  + ChatColor.BOLD +
            "残り" + ChatColor.YELLOW + ChatColor.BOLD + plugin.days + ChatColor.WHITE + ChatColor.BOLD +
            "日" + ChatColor.YELLOW + ChatColor.BOLD + plugin.hours + ChatColor.WHITE + ChatColor.BOLD +
            "時間" + ChatColor.YELLOW + ChatColor.BOLD +  plugin.minutes + ChatColor.WHITE + ChatColor.BOLD +
            "分")
    countDown.score = 13

    val allScore = plugin.objective!!.getScore("" + ChatColor.BLUE + ChatColor.BOLD +
            "全体のスコア  " + ChatColor.YELLOW + ChatColor.BOLD +
            " : " + ChatColor.GREEN + ChatColor.BOLD +
            plugin.allScore)
    allScore.score = 11

        val space1 = plugin.objective!!.getScore("  ")
        space1.score = 10

        val space2 = plugin.objective!!.getScore(" ")
        space2.score = 12

    for ((count) in plugin.playerData.indices.withIndex())
    {

        when (count) {

            0 -> prefix = "" + ChatColor.YELLOW + ChatColor.BOLD + "♔第1位♔ " + ChatColor.WHITE + ChatColor.BOLD
            1 -> prefix = "" + ChatColor.GRAY + ChatColor.BOLD + "♕第2位♕ " + ChatColor.WHITE + ChatColor.BOLD
            2 -> prefix = "" + ChatColor.GOLD + ChatColor.BOLD + "♖第3位♖ " + ChatColor.WHITE + ChatColor.BOLD
            3 -> prefix = "" + ChatColor.WHITE + ChatColor.BOLD + "♗第4位♗ " + ChatColor.WHITE + ChatColor.BOLD
            4 -> prefix = "" + ChatColor.WHITE + ChatColor.BOLD + "♘第5位♘ " + ChatColor.WHITE + ChatColor.BOLD
            5, 6, 7, 8, 9 -> prefix = "" + ChatColor.WHITE + ChatColor.BOLD + "♙第${count + 1}位♙ " + ChatColor.WHITE + ChatColor.BOLD

        }

        val score: Score = plugin.objective!!.getScore(prefix + plugin.playerData[count]!!.playerName +
                " : " + ChatColor.GREEN + ChatColor.BOLD +
                plugin.playerData[count]!!.score)
        score.score = 9 - count

        for (player in Bukkit.getOnlinePlayers()) {
            player.scoreboard = plugin.scoreboard!!
            }
        }
    }
}



