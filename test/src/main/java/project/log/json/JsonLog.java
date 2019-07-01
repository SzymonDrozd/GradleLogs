package project.log.json;

import project.enums.LogState;

public class JsonLog implements Comparable<JsonLog>  {
    private String id;
    private LogState state;
    private String type;
    private String host;
    private Long timestamp;

    public JsonLog() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogState getState() {
        return state;
    }

    public void setState(LogState state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(JsonLog log) {
        if (log.getState().equals(LogState.STARTED)) {
            return 1;
        } else {
            return -1;
        }
    }
}
