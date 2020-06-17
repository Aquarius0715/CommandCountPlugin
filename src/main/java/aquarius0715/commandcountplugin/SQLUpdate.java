package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUpdate extends Thread {

    CommandCountPlugin plugin;

    public SQLUpdate(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreBoardStatsFalse(Player player) {

        Bukkit.getScheduler().runTask(plugin, this);
        {

            if (!sqlConnectSafely()) {
                return;
            }
            String sql = "update commandCountTable set scoreBoardStats = false " +
                    "WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.getUniqueId().toString() + "';";
            plugin.MySQLManager.execute(sql);
        }
    }

    public void updateScoreBoardTrue(Player player) {

        Bukkit.getScheduler().runTask(plugin, this);
        {

            if (!sqlConnectSafely()) {
                return;
            }
            String sql = "update commandCountTable set scoreBoardStats = true " +
                    "WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.getUniqueId().toString() + "';";
            plugin.MySQLManager.execute(sql);
        }
    }

    public void updateScore(Player player) throws SQLException {

        Bukkit.getScheduler().runTask(plugin, this);
        {

            if (!sqlConnectSafely()) {
                return;
            }

            if (!plugin.joinOnTheWay) {
                player.sendMessage("途中参加は許可されていません。");
                return;
            }

            String sql = "SELECT cmdCount FROM commandCountTable WHERE StartDate = '"
                    + plugin.StartDate + "' AND UUID = '"
                    + player.getUniqueId().toString() + "';";
            ResultSet resultSet = plugin.MySQLManager.query(sql);

            resultSet.next();

            int score = resultSet.getInt("cmdCount") + 1;

            String sql1 = "UPDATE commandCountTable set cmdCount = "
                    + score
                    + " WHERE StartDate = '"
                    + plugin.StartDate
                    + "' AND UUID = '"
                    + player.getUniqueId().toString() + "';";
            plugin.MySQLManager.execute(sql1);
            player.sendMessage(ChatColor.GRAY + "スコアが1増えました。");
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