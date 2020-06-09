package aquarius0715.commandcountplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Date;
import java.util.Objects;

public final class CommandCountPlugin extends JavaPlugin {

    boolean pluginStats = false;

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

    Date startDate;
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
