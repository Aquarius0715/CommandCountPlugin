package aquarius0715.commandcountplugin;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoardData {

    CommandCountPlugin plugin;

    public ScoreBoardData(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void addScoreBoard() {

        plugin.first.addEntry("1位 ");
        plugin.first.setSuffix(plugin.rankingDisplayName[0] + " : " + "スコア " + plugin.rankingScore[0]);
        plugin.objective.getScore("1位 ").setScore(9);

        plugin.second.addEntry("2位 ");
        plugin.second.setSuffix(plugin.rankingDisplayName[1] + " : " + "スコア " + plugin.rankingScore[1]);
        plugin.objective.getScore("2位 ").setScore(8);

        plugin.third.addEntry("3位 ");
        plugin.third.setSuffix(plugin.rankingDisplayName[2] + " : " + "スコア " + plugin.rankingScore[2]);
        plugin.objective.getScore("3位 ").setScore(7);

        plugin.fourth.addEntry("4位 ");
        plugin.fourth.setSuffix(plugin.rankingDisplayName[3] + " : " + "スコア " + plugin.rankingScore[3]);
        plugin.objective.getScore("4位 ").setScore(6);

        plugin.fifth.addEntry("5位 ");
        plugin.fifth.setSuffix(plugin.rankingDisplayName[4] + " : " + "スコア " + plugin.rankingScore[4]);
        plugin.objective.getScore("5位 ").setScore(5);

        plugin.sixth.addEntry("6位 ");
        plugin.sixth.setSuffix(plugin.rankingDisplayName[5] + " : " + "スコア " + plugin.rankingScore[5]);
        plugin.objective.getScore("6位 ").setScore(4);

        plugin.seventh.addEntry("7位 ");
        plugin.seventh.setSuffix(plugin.rankingDisplayName[6] + " : " + "スコア " + plugin.rankingScore[6]);
        plugin.objective.getScore("7位 ").setScore(3);

        plugin.eighth.addEntry("8位 ");
        plugin.eighth.setSuffix(plugin.rankingDisplayName[7] + " : " + "スコア " + plugin.rankingScore[7]);
        plugin.objective.getScore("8位 ").setScore(2);

        plugin.ninth.addEntry("9位 ");
        plugin.ninth.setSuffix(plugin.rankingDisplayName[8] + " : " + "スコア " + plugin.rankingScore[8]);
        plugin.objective.getScore("9位 ").setScore(1);

        plugin.tenth.addEntry("10位 ");
        plugin.tenth.setSuffix(plugin.rankingDisplayName[9] + " : " + "スコア " + plugin.rankingScore[9]);
        plugin.objective.getScore("10位 ").setScore(0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(plugin.scoreboard);
        }
    }
}
