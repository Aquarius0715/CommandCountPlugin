package aquarius0715.commandcountplugin

import java.text.SimpleDateFormat
import java.util.*

class DateFormant(var plugin: CommandCountPlugin) {
    fun StartTime() {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss")
        plugin.StartDate = simpleDateFormat.format(date)
    }

}