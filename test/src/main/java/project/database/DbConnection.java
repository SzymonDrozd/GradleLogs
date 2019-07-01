package project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static String connectionString = "jdbc:hsqldb:file:db-data/mydatabase";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString, "SA", "");
        return connection;
    }
    public static String createLogTable(){
        String createDbLog = "create table if not exists\n" +
                "\tdb_log(id varchar(50), type varchar(50), host varchar(50), duration bigint, alert boolean);";
        return createDbLog;
    }
}
