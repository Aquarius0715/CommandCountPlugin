package aquarius0715.commandcountplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CommandCountPlugin extends JavaPlugin {

    PlayerData pd = new PlayerData();
    List<PlayerData> playerData = new ArrayList<PlayerData>(10);

    class PlayerData {
        String playerName;
        int score;
    }

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
