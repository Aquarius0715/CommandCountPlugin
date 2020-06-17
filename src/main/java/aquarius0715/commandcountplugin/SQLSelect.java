package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SQLSelect extends Thread {


    CommandCountPlugin plugin;

    public SQLSelect(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void selectScoreBoardStats(Player player) throws SQLException {

        Bukkit.getScheduler().runTask(plugin, this);
        {

            if (!sqlConnectSafely()) {
                plugin.MySQLManager.close();
                return;
            }

            if (!plugin.joinOnTheWay) {
                player.sendMessage("あなたはまだレイドに参加していません。");
                return;
            }

            String sql = "SELECT scoreBoardStats FROM commandCountTable WHERE StartDate = '" + plugin.StartDate + "' AND UUID = '"
                    + player.getUniqueId().toString() + "';";
            ResultSet resultSet = plugin.MySQLManager.query(sql);


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
            plugin.MySQLManager.close();
        }
    }

    public void selectPlayerScoreRanking() throws SQLException {

        Bukkit.getScheduler().runTask(plugin, this);
        {

            if (!sqlConnectSafely()) {
                plugin.MySQLManager.close();
                return;
            }

            String sql = "SELECT * from commandCountTable where StartDate = '" + plugin.StartDate + "' ORDER BY cmdCount DESC LIMIT 10;";

            ResultSet resultSet = plugin.MySQLManager.query(sql);


            plugin.playerData.clear();

            while (resultSet.next()) {
                plugin.playerData.add(new CommandCountPlugin.PlayerData(resultSet.getString("playerName"), resultSet.getInt("cmdCount")));
            }

            String sql1 = "SELECT SUM(cmdCount) from commandCountTable where StartDate = '" + plugin.StartDate + "';";
            ResultSet resultSet1 = plugin.MySQLManager.query(sql1);
            resultSet1.next();

            plugin.allScore = resultSet1.getInt("SUM(cmdCount)");
            plugin.MySQLManager.close();
        }
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