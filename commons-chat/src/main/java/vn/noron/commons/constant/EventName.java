package vn.noron.commons.constant;

public enum EventName {
    API_BROAD_CAST_MESSAGE("api.broad_cast_message"),
    UNKNOWN("unknown");
    private String name;

    private EventName(String send) {
        this.name = send;
    }

    public static EventName from(String func) {
        for (EventName eventName : values()) {
            if (eventName.value().equalsIgnoreCase(func))
                return eventName;
        }

        return UNKNOWN;
    }

    public String value() {
        return name;
    }
}
