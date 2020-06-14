package aquarius0715.commandcountplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Objects;

public final class CommandCountPlugin extends JavaPlugin {

    boolean pluginStats = true;
    boolean gameStats = false;
    boolean joinOnTheWay = true;

    ScoreboardManager scoreboardManager;
    Scoreboard scoreboard;
    Objective objective;

    ScoreBoard scoreBoard = new ScoreBoard(this);
    ScoreBoardData scoreBoardData = new ScoreBoardData(this);
    SQLInsert sqlInsert = new SQLInsert(this);
    MySQLManager MySQLManager = new MySQLManager(this, "CommandCountPlugin");
    SQLSelect sqlSelect = new SQLSelect(this);
    SQLUpdate sqlUpdate = new SQLUpdate(this);
    DateFormant dateFormant = new DateFormant(this);
    Timer timer = new Timer(this);


    ArrayList<String> playerName = new ArrayList<String>(10);
    ArrayList<Integer> score = new ArrayList<Integer>(10);
    ArrayList<Team> team = new ArrayList<Team>(10);

    String StartDate;

    int time = 0;


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
