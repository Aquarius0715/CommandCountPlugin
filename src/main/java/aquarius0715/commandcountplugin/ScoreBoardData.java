package aquarius0715.commandcountplugin;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoardData {

    CommandCountPlugin plugin;

    public ScoreBoardData(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void addTenthBoard(String displayName, int score) {
        plugin.first = plugin.scoreboard.registerNewTeam("first");
        plugin.first.addEntry("1位 ");
        plugin.first.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("1位 ").setScore(9);

        plugin.second = plugin.scoreboard.registerNewTeam("second");
        plugin.second.addEntry("2位 ");
        plugin.second.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("2位 ").setScore(8);

        plugin.third = plugin.scoreboard.registerNewTeam("third");
        plugin.third.addEntry("3位 ");
        plugin.third.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("3位 ").setScore(7);

        plugin.fourth = plugin.scoreboard.registerNewTeam("fourth");
        plugin.fourth.addEntry("4位 ");
        plugin.fourth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("4位 ").setScore(6);

        plugin.fifth = plugin.scoreboard.registerNewTeam("fifth");
        plugin.fifth.addEntry("5位 ");
        plugin.fifth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("5位 ").setScore(5);

        plugin.sixth = plugin.scoreboard.registerNewTeam("sixth");
        plugin.sixth.addEntry("6位 ");
        plugin.sixth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("6位 ").setScore(4);

        plugin.seventh = plugin.scoreboard.registerNewTeam("seventh");
        plugin.seventh.addEntry("7位 ");
        plugin.seventh.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("7位 ").setScore(3);

        plugin.eighth = plugin.scoreboard.registerNewTeam("eighth");
        plugin.eighth.addEntry("8位 ");
        plugin.eighth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("8位 ").setScore(2);

        plugin.ninth = plugin.scoreboard.registerNewTeam("ninth");
        plugin.ninth.addEntry("9位 ");
        plugin.ninth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("9位 ").setScore(1);

        plugin.tenth = plugin.scoreboard.registerNewTeam("tenth");
        plugin.tenth.addEntry("10位 ");
        plugin.tenth.setSuffix(displayName + " : " + "スコア " + score);
        plugin.objective.getScore("10位 ").setScore(0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(plugin.scoreboard);
        }
    }
}
