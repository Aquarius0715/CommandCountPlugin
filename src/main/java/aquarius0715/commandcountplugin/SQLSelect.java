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

        String sql = "SELECT scoreBoardStats FROM commandCountTable WHERE StartDate = '" + plugin.StartDate + "' AND UUID = '"
                + player.getUniqueId().toString() + "';";
        ResultSet resultSet = plugin.MySQLManager.query(sql);

        if (resultSet == null) {
            if (!plugin.joinOnTheWay) {
                player.sendMessage("途中参加は許可されていません。");
                return;
            }
            player.sendMessage("途中から参加しました。");
            plugin.sqlInsert.insertDefaultTable(player);
        }

        requireNonNull(resultSet).next();

        if (Objects.requireNonNull(resultSet).getBoolean("scoreBoardStats")) {
            player.sendMessage("スコアボードを非表示にしました");
            plugin.sqlUpdate.updateScoreBoardStatsFalse(player);
            player.setScoreboard(requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
        } else {
            player.sendMessage("スコアボードを表示にしました");
            plugin.sqlUpdate.updateScoreBoardTrue(player);
            player.setScoreboard(plugin.scoreboard);
        }
    }

    public void selectPlayerScore(Player player) throws SQLException {
        if (!sqlConnectSafely()) {
            return;
        }

        String sql = "SELECT cmdCount FROM commandCountTable WHERE UUID = '" + player.getUniqueId().toString() + "' AND StartDate = '"
                + plugin.StartDate + "';";
        ResultSet resultSet = plugin.MySQLManager.query(sql);

        if (resultSet == null) {
            if (!plugin.joinOnTheWay) {
                player.sendMessage("途中参加は許可されていません。");
                return;
            }
            player.sendMessage("途中から参加しました。");
            plugin.sqlInsert.insertDefaultTable(player);
        }

        requireNonNull(resultSet).next();

        plugin.scoreBoardData.addScoreBoard();
    }

    public void selectPlayerScoreRanking() throws SQLException {
        if (!sqlConnectSafely()) {
            return;
        }
        selectScore(1);
        selectScore(2);
        selectScore(3);
        selectScore(4);
        selectScore(5);
        selectScore(6);
        selectScore(7);
        selectScore(8);
        selectScore(9);
        selectScore(10);
    }

    public boolean sqlConnectSafely() {
        if (!plugin.MySQLManager.connectCheck()) {
            Bukkit.broadcastMessage("DB接続に失敗したためプラグインを停止します。");
            plugin.pluginStats = false;
            return false;
        }
        return true;
    }

    public void selectScore(int joinPlayers) throws SQLException {
        if (Bukkit.getOnlinePlayers().size() >= joinPlayers) {
            String sql = "SELECT * from commandCountTable where StartDate = '" + plugin.StartDate + "' ORDER BY cmdCount DESC LIMIT 10 OFFSET " + (joinPlayers - 1) + ";";
            ResultSet resultSet = plugin.MySQLManager.query(sql);

            resultSet.next();
            plugin.rankingScore[joinPlayers - 1] = resultSet.getInt("cmdCount");
            plugin.rankingDisplayName[joinPlayers - 1] = resultSet.getString("playerName");
        }
    }
}