package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.database.DbConnection;
import project.thread.LogManager;
import project.view.MainView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static LogManager logManager = new LogManager();


    public static void main(String[] args) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            LOG.warn("Error: " + e);
        }
        try (Connection connection = DbConnection.getConnection()){
            LOG.debug("Creating log table if doesnt exist");
            connection.createStatement().executeUpdate(DbConnection.createLogTable());
            logManager.start();
        } catch (SQLException e) {
            LOG.warn("Sql Error: " + e);
        }  catch (Throwable e){
            LOG.warn("Error: " + e);

        }
        new MainView();
    }
}
