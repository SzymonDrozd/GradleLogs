package project.log.service;

import project.log.json.JsonLog;
import project.log.repository.LogRepository;
import project.log.task.LogTask;
import project.model.DbLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LogService {
    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);

    private LogRepository logRepository;
    private int quantityOfTask = 100;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLog(Connection connection, DbLog log) throws SQLException {
        logRepository.saveLog(connection, log);
    }

    public List<LogTask> getTasksBasedOnLogs(Map<String, List<JsonLog>> map) {
        return toLogTask(toDbLog(map));
    }

    private List<DbLog> toDbLog(Map<String, List<JsonLog>> map) {
        LOG.debug("Parsing 2 logs from json into one log");
        List<DbLog> ret = new ArrayList<>();
        for (String key : map.keySet()) {
            List<JsonLog> list = map.get(key);
            Collections.sort(list);
            JsonLog log1 = list.get(0);
            JsonLog log2 = list.get(1);
            Long duration = log2.getTimestamp() - log1.getTimestamp();
            boolean alert = duration > 4;

            DbLog log = new DbLog();
            log.setId(log1.getId());
            log.setType(log1.getType());
            log.setHost(log1.getHost());
            log.setDuration(duration);
            log.setAlert(alert);
            ret.add(log);
        }
        return ret;
    }

    private List<LogTask> toLogTask(List<DbLog> listOfLogs) {
        LOG.debug("Creating list of tasks");
        List<LogTask> tasks = new ArrayList<>();
        int amountOfPackage = listOfLogs.size() / quantityOfTask;
        if((listOfLogs.size() % quantityOfTask) > 0) {
            amountOfPackage++;
        }
        for (int i = 0; i < amountOfPackage; i++) {
            List<DbLog> listForTask;
            if(i == amountOfPackage - 1) {
                listForTask = listOfLogs.subList(i, listOfLogs.size());
            } else {
                listForTask = listOfLogs.subList(i, (i + 1) * quantityOfTask);
            }
            LogTask task = new LogTask(this, listForTask);
            tasks.add(task);
        }
        LOG.debug("Tasks created: " + tasks.size());
        return tasks;
    }
}
