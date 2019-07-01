package log.json;

import org.junit.Assert;
import org.junit.Test;
import project.log.json.JsonLog;
import project.log.json.JsonToLogParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonToLogParserTest {
    final JsonToLogParser jsonToLogParser = new JsonToLogParser();

    @Test
    public void loadFileFromPathShouldReturnNull() {
        String path = "pathToFile";
        Assert.assertEquals(null, jsonToLogParser.loadFileFromPath(path));
    }

    @Test
    public void loadFileFromPathShouldReturnList() {
        String path = "pathToFile.json";
        Assert.assertEquals(new ArrayList<JsonLog>().getClass(), jsonToLogParser.loadFileFromPath(path).getClass());
    }

    @Test
    public void groupJsonLogShouldReturnMapWithSameSize() {
        //given
        List<String> ids = Arrays.asList("id1", "id2", "id3");
        List<JsonLog> logs = new ArrayList<>();
        for(String id : ids){
            JsonLog log1 = new JsonLog();
            log1.setId(id);
            log1.setHost("test1" + id);

            JsonLog log2 = new JsonLog();
            log2.setId(id);
            log2.setHost("test2" + id);

            logs.add(log1);
            logs.add(log2);
        }
        Map<String, List<JsonLog>> map = jsonToLogParser.groupJsonLog(logs);
        Assert.assertEquals(ids.size(), map.size());
    }
}