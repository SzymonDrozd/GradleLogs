package log.service;

import project.enums.LogState;
import project.log.json.JsonLog;
import project.log.repository.LogRepository;
import project.log.service.LogService;
import project.log.task.LogTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class LogServiceTest {

    final LogService logService = new LogService(new LogRepository());

    @Test
    public void getTasksBasedOnLogsShouldReturnSameSize(){
        List<String> ids = Arrays.asList("id1", "id2", "id3");
        Map<String,List<JsonLog>> logs = new HashMap<>();
        for(String id : ids){
            List<JsonLog> list = new ArrayList();
            JsonLog log1 = new JsonLog();
            log1.setId(id);
            log1.setTimestamp(6L);
            log1.setState(LogState.FINISHED);
            log1.setHost("test1" + id);

            JsonLog log2 = new JsonLog();
            log2.setId(id);
            log2.setTimestamp(2L);
            log2.setState(LogState.STARTED);
            log2.setHost("test2" + id);

            list.add(log1);
            list.add(log2);
            logs.put(id, list);
        }
        List<LogTask> tasks = logService.getTasksBasedOnLogs(logs);
        Assert.assertEquals(1, tasks.size());

    }


}