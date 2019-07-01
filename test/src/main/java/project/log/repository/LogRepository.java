package project.log.repository;

import project.model.DbLog;

import java.sql.Connection;
import java.sql.SQLException;

public class LogRepository {


    public void saveLog(Connection connection, DbLog log) throws SQLException {
        System.out.println("Save Log: " + log.getId());
        connection.createStatement()
                .executeUpdate(insertLog(log.getId(), log.getType(), log.getHost(), log.getDuration(), log.isAlert()));
    }

    private String insertLog(String id, String type, String host, Long duration, boolean alert) {
        String insertLog = "INSERT INTO db_log VALUES('" + id + "', '" + type + "', '" + host + "', " + duration + ", "
                + alert + ");";
        return insertLog;
    }

}
