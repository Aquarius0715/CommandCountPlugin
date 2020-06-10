package aquarius0715.commandcountplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;

import static java.util.Objects.requireNonNull;

public class Commands implements CommandExecutor {

    CommandCountPlugin plugin;

    public Commands(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("cmdcount")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot this");
                return true;
            }


            if (args.length == 0) {
                if (plugin.pluginStats) {
                    sender.sendMessage("CommandCountPluginは有効です。");
                    return true;
                } else
                    sender.sendMessage("CommandCountPluginは無効です");
                return true;
            }
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています");
                    return false;
                } else {
                    sender.sendMessage("cmdCounter");
                    //TODO Write help text
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("reset")) {
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています");
                    return false;
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。");
                        return false;
                    } else {
                        if (!sender.hasPermission("admin")) {
                            sender.sendMessage("あなたはこのコマンドを使用することができません");
                            return false;
                        } else {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.setScoreboard(requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
                            }
                            return true;
                        }
                    }
                }
            }

            if (args[0].equalsIgnoreCase("start")) {
                Player player = (Player) sender;
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。");
                    return false;
                } else {
                    if (plugin.gameStats) {
                        sender.sendMessage("レイドは既に始まっています。");
                        return false;
                    } else {
                        if (!sender.hasPermission("admin")) {
                            sender.sendMessage("あなたはこのコマンドを使用することができません");
                            return false;
                        } else {

                            plugin.gameStats = true;

                            plugin.sqlInsert.insertDefaultTable(player);

                            try {
                                plugin.scoreBoard.createScoreBoard();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            plugin.StartDate = new Date().toString();

                            plugin.dateFormant.FormStartTime();

                            plugin.scoreBoard.updateScoreBoard();

                            sender.sendMessage("レイドが始まりました。");
                            return true;
                        }
                    }
                }
            }

            if (args[0].equalsIgnoreCase("scoreboard")) {
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。");
                    return false;
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。");
                    }
                }
                try {
                    plugin.sqlSelect.selectScoreBoardStats(((Player) sender).getPlayer());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                Player player = (Player) sender;
                if (!plugin.pluginStats) {
                    sender.sendMessage("プラグインが無効になっています。");
                    return false;
                } else {
                    if (!plugin.gameStats) {
                        sender.sendMessage("レイドはまだ始まっていません。");
                        return false;
                    }
                    try {
                        plugin.sqlUpdate.updateScore(player);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("on")) {
                if (!plugin.pluginStats) {
                    plugin.pluginStats = true;
                    sender.sendMessage("プラグインを有効にしました。");
                    return true;
                } else
                    sender.sendMessage("プラグインはすでに有効です。");
            }

            if (args[0].equalsIgnoreCase("off")) {
                if (!plugin.pluginStats) {
                    plugin.pluginStats = false;
                    sender.sendMessage("プラグインを無効にしました");
                    return true;
                } else
                    sender.sendMessage("プラグインはすでに無効です。");
            }
        }
        return false;
    }


}
