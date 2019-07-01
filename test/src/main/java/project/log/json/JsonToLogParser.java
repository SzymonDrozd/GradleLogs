package project.log.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToLogParser {

    private static final Logger LOG = LoggerFactory.getLogger(JsonToLogParser.class);

    @SuppressWarnings("unchecked")
    private List<JsonLog> jsonToLogsList(File file){
        LOG.debug("Start parsing json file");
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonLog> list = null;
        try {
            list = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonLog.class));
            LOG.debug("Parsing successfull");
        } catch (IOException e) {
            LOG.warn("Parse error: " + e);
        }
        return list;
    }

    public List<JsonLog> loadFileFromPath(String path){
        if(!path.endsWith(".json")) {
            LOG.warn("Wrong file format, this is not json file");
            return null;
        }
        return jsonToLogsList(new File(path));
    }

    public Map<String, List<JsonLog>> groupJsonLog(List<JsonLog> logs){
        LOG.debug("Grouping JsonLogs by id");
        Map<String, List<JsonLog>> map = new HashMap<>();
        for(JsonLog log : logs) {
            if (!map.containsKey(log.getId())) {
                List<JsonLog> list = new ArrayList<JsonLog>();
                list.add(log);

                map.put(log.getId(), list);
            } else {
                map.get(log.getId()).add(log);
            }
        }

        return map;
    }

}
