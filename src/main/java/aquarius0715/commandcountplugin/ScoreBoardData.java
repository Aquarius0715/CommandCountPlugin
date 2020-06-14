package aquarius0715.commandcountplugin;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoardData {

    CommandCountPlugin plugin;

    public ScoreBoardData(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreBoard() {

        int count = 0;
        for (int i = 0; i < plugin.playerName.size(); i++) {
            plugin.team.get(count).addEntry(count + 1 + "位");
            plugin.team.get(count).setSuffix(plugin.playerName.get(count) + " : " + "スコア" + plugin.score.get(count));
            plugin.objective.getScore(count + 1 + "位").setScore(9 - count);
            count++;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(plugin.scoreboard);
        }
    }
}
