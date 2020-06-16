package aquarius0715.commandcountplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

    CommandCountPlugin plugin;

    public Events(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.pluginStats && !plugin.joinOnTheWay) {
            event.getPlayer().sendMessage("只今レイドが行われています。");
            event.getPlayer().setScoreboard(plugin.scoreboard);
        }
    }


}
