package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLInsert {

    CommandCountPlugin plugin;

    public SQLInsert(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void insertDefaultTable(Player player) {
        if (!sqlConnectSafely()) {
            return;
        }
        String sql = "INSERT INTO commandCountTable (StartDate, UUID, playerName, cmdCount, scoreBoardStats) VALUE(" + "'"
                + plugin.startDate + "' , '"
                + player.getUniqueId().toString() + "' , '"
                + player.getDisplayName() + "' , "
                + 0 + " , "
                + true + ");";
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

    public void FormStartTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
        plugin.startDate = simpleDateFormat.format(date);
    }
}
