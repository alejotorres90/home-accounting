package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection = null;

    public Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed() || connection.isValid(10000)) {
            connection = DriverManager.getConnection("jdbc:mysql://den1.mysql4.gear.host/basetest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "basetest", "<testtest>");
        }

        return connection;
    }

}