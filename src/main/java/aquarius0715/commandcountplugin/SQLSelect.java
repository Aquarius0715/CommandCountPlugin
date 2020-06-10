package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SQLSelect {

    CommandCountPlugin plugin;

    public SQLSelect(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void selectScoreBoardStats(Player player) throws SQLException {
        if (!sqlConnectSafely()) {
            return;
        }

        String sql = "SELECT scoreBoardStats FROM commandCountTable WHERE UUID = '" + player.getUniqueId().toString() + "' ORDER BY Id LIMIT 1;";
        ResultSet resultSet = plugin.MySQLManager.query(sql);
        if (resultSet == null) {
            player.sendMessage("データベースにあなたの情報がありません。登録します。");
            plugin.sqlInsert.insertDefaultTable(player);
        }

        if (Objects.requireNonNull(resultSet).getBoolean("scoreBoardStats")) {
            player.sendMessage("スコアボードを非表示にしました");
            plugin.sqlUpdate.updateScoreBoardStatsFalse(player);
            player.setScoreboard(requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
        } else {
            player.sendMessage("スコアボードを表示にしました");
            plugin.sqlUpdate.updateScoreBoardTrue(player);
            plugin.scoreBoard.createScoreBoard();
        }
    }

    public void selectPlayerScore(Player player) throws SQLException {
        if (!sqlConnectSafely()) {
            return;
        }

        String sql = "SELECT cmdCount FROM commandCountTable WHERE UUID = '" + player.getUniqueId().toString() + "' AND StartDate = '"
                + plugin.startDate + "';";
        ResultSet resultSet = plugin.MySQLManager.query(sql);
        if (resultSet == null) {
            player.sendMessage("データベースにあなたの情報がありません。登録します");
            plugin.sqlInsert.insertDefaultTable(player);
            return;
        }

        plugin.scoreBoardData.addScoreBoard(player.getDisplayName(), resultSet.getInt("cmdCount")); //TODO java.sql.SQLException: Illegal operation on empty result set.
    }

    public boolean sqlConnectSafely() {
        if (!plugin.MySQLManager.connectCheck()) {
            Bukkit.broadcastMessage("DB接続に失敗したためプラグインを停止します。");
            plugin.pluginStats = false;
            return false;
        }
        return true;
    }
}
