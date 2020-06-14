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

        int count = 0;
        for (int i = 0; i < plugin.playerData.size(); i++) {
            Score score = plugin.objective.getScore("1位 : " + plugin.playerData.get(count).playerName + " : " + plugin.playerData.get(count).score);
            score.setScore(9 - count);
            count++;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(plugin.scoreboard);
        }
    }
}
