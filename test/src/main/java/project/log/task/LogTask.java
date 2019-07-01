package project.log.task;

import project.database.DbConnection;
import project.log.service.LogService;
import project.model.DbLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.thread.LogManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogTask implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(LogTask.class);

    private List<DbLog> listOfData;
    private LogService logService;

    public LogTask(LogService logService, List<DbLog> list) {
        this.logService = logService;
        this.listOfData = list;
    }

    @Override
    public void run() {
        LOG.debug("Starting task");
        System.out.println("Starting task");
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            for(DbLog log : listOfData) {
                logService.saveLog(connection, log);
            }
            LOG.debug("Task ended");
        } catch (SQLException e) {
            LOG.warn("Error" + e);
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Error: " + e);
            }
        }
        LogManager.threadCounter++;
    }
}
