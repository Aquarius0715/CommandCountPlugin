package aquarius0715.commandcountplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CommandCountPlugin extends JavaPlugin {

    boolean pluginStats = true;
    boolean gameStats = false;
    boolean joinOnTheWay = true;

    ScoreboardManager scoreboardManager;
    Scoreboard scoreboard;
    Objective objective;

    Team first;
    Team second;
    Team third;
    Team fourth;
    Team fifth;
    Team sixth;
    Team seventh;
    Team eighth;
    Team ninth;
    Team tenth;

    ScoreBoard scoreBoard = new ScoreBoard(this);
    ScoreBoardData scoreBoardData = new ScoreBoardData(this);
    SQLInsert sqlInsert = new SQLInsert(this);
    MySQLManager MySQLManager = new MySQLManager(this, "CommandCountPlugin");
    SQLSelect sqlSelect = new SQLSelect(this);
    SQLUpdate sqlUpdate = new SQLUpdate(this);
    DateFormant dateFormant = new DateFormant(this);

    String[] rankingDisplayName = new String[10];


    int[] rankingScore = new int[10];

    String StartDate;

    Map<String, Integer> ScoreBoardNumber = new HashMap<String, Integer>();

    Date finishDate;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Objects.requireNonNull(getCommand("cmdcount")).setExecutor(new Commands(this));
        MySQLManager = new MySQLManager(this, "CommandCountPlugin");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
