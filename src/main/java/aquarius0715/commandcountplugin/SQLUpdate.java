package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUpdate {

    CommandCountPlugin plugin;

    public SQLUpdate(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateScoreBoardStatsFalse(Player player) {
        if (!sqlConnectSafely()) {
            return;
        }
        String sql = "UPDATE commandCountTable set scoreBoardStats " +
                "= (select replace(scoreBoardStats, true, false)) " +
                "WHERE StartDate = '"
                + plugin.startDate
                + "' AND UUID = '"
                + player.getUniqueId() + "';";
        plugin.MySQLManager.execute(sql);
    }

    public void updateScoreBoardTrue(Player player) {
        if (!sqlConnectSafely()) {
            return;
        }
        String sql = "UPDATE commandCountTable set scoreBoardStats " +
                "= (select replace(scoreBoardStats, false, true)) " +
                "WHERE StartDate = '"
                + plugin.startDate.toString()
                + "' AND UUID = '"
                + player.getUniqueId().toString() + "';";
        plugin.MySQLManager.execute(sql);
    }

    public void updateScore(Player player) throws SQLException {
        if (!sqlConnectSafely()) {
            return;
        }

        String sql = "SELECT cmdCount FROM commandCountTable WHERE StartDate = '"
                + plugin.startDate.toString() + "' AND UUID = '"
                + player.getUniqueId().toString() + "';";
        ResultSet resultSet = plugin.MySQLManager.query(sql);

        String sql1 = "UPDATE commandCountTable set cmdScore " +
                "= (select replace(cmdCount, "
                + resultSet.getInt("cmdCount") + ", " //TODO java.sql.SQLException: Illegal operation on empty result set.
                + resultSet.getInt("cmdCount") + 1 + ")) "
                + "WHERE StartDate = '"
                + plugin.startDate.toString()
                + "' AND UUID = '"
                + player.getUniqueId().toString() + "';";
        plugin.MySQLManager.execute(sql1);
    }

    public void updateStartDate(Player player) { //TODO メソッドを呼び出したときにStartDateに代入されない
        if (!sqlConnectSafely()) {
            return;
        }

        String sql = "UPDATE commandCountTable set StartDate = '"
                + plugin.startDate.toString()
                + "' WHERE UUID = '"
                + player.getUniqueId().toString()
                + "' + AND StartDate IS NULL;";
        plugin.MySQLManager.execute(sql);
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
