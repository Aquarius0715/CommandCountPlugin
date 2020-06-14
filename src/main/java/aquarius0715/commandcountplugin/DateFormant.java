package aquarius0715.commandcountplugin;


import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormant {

    CommandCountPlugin plugin;

    public DateFormant(CommandCountPlugin plugin) {
        this.plugin = plugin;
    }

    public void FormStartTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
        plugin.StartDate = simpleDateFormat.format(date);
    }
}
