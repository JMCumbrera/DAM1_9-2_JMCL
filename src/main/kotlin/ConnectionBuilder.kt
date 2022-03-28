import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionBuilder {
    // TODO Auto-generated catch block
    lateinit var connection: Connection
    private val jdbcURL = "jdbc:postgresql://localhost:5432/postgres"
    private val jdbcUsername = "postgres"
    private val jdbcPassword = "cvink1925"

    init {
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)
            connection.autoCommit = false
        } catch (e: SQLException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}