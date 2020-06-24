package aquarius0715.commandcountplugin

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.ScoreboardManager
import java.util.*

class CommandCountPlugin : JavaPlugin() {
    var playerData: MutableList<PlayerData?> = ArrayList(10)
    var allScore = 0
    var pluginStats = true
    var gameStats = false
    var scoreboardManager: ScoreboardManager? = null
    var scoreboard: Scoreboard? = null
    var objective: Objective? = null
    var scoreBoard = ScoreBoard(this)
    var scoreBoardData = ScoreBoardData(this)
    var sqlInsert = SQLInsert(this)
    var MySQLManager = MySQLManager(this, "CommandCount")
    var sqlSelect = SQLSelect(this)
    var sqlUpdate = SQLUpdate(this)
    var dateFormant = DateFormant(this)
    var timer = Timer(this)
    var StartDate: String? = null
    var time = 0
    var addScoreOp = 0
    var days = 0
    var hours = 0
    var minutes = 0
    var seconds = 0
    var prefix = "§l[§d§lM§f§la§a§ln§f§l10§b§lレイド§f§l]"
    override fun onEnable() {
        saveDefaultConfig()
        Objects.requireNonNull(getCommand("cmdcount"))!!.setExecutor(Commands(this))
        server.pluginManager.registerEvents(Events(this), this)
        MySQLManager = MySQLManager(this, "CommandCountPlugin")
    }

    class PlayerData(var playerName: String, var score: Int)

    override fun onDisable() {
        // Plugin shutdown logic
    }
}