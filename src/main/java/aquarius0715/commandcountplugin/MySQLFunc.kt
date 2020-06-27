package aquarius0715.commandcountplugin

import org.bukkit.Bukkit
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level

/**
 * Created by takatronix on 2017/03/05.
 */
class MySQLFunc(host: String?, db: String?, user: String?, pass: String?, port: String?) {
    var HOST: String? = null
    var DB: String? = null
    var USER: String? = null
    var PASS: String? = null
    var PORT: String? = null
    var con: Connection? = null
    fun open(): Connection? {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB + "?useSSL=false", USER, PASS)
            return con
        } catch (var2: SQLException) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not connect to MySQL server, error code: " + var2.errorCode)
        } catch (var3: ClassNotFoundException) {
            Bukkit.getLogger().log(Level.SEVERE, "JDBC driver was not found in this machine.")
        }
        return con
    }

    fun checkConnection(): Boolean {
        return con != null
    }

    fun close(c: Connection?) {
        c?.close()
    }

    init {
        HOST = host
        DB = db
        USER = user
        PASS = pass
        PORT = port
    }
}