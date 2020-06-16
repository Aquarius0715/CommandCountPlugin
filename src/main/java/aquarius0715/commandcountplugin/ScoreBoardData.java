package aquarius0715.commandcountplugin;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class ScoreBoardData {


    CommandCountPlugin plugin;

    public ScoreBoardData(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreBoard() {

        plugin.scoreBoard.createScoreBoard();
        Score score1 = plugin.objective.getScore("全体のスコア : " + plugin.allScore);
        score1.setScore(10);

        int count = 0;
        for (int i = 0; i < plugin.playerData.size(); i++) {
            Score score = plugin.objective.getScore(count + 1 + "位 : " + plugin.playerData.get(count).playerName + " : " + plugin.playerData.get(count).score);
            score.setScore(9 - count);
            count++;
        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(plugin.scoreboard);
        }
    }
}
