package vn.noron.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.noron.core.log.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static vn.noron.config.JsonMapper.getObjectMapper;
import static vn.noron.core.json.Json.decodeValue;

public class JsonObject {
    private static final Logger log = Logger.getLogger(JsonObject.class);
    private Map<String, Object> map;

    /**
     * Create an instance from a string of JSON
     *
     * @param json the string of JSON
     */
    public JsonObject(String json) {
        fromJson(json);
    }


    /**
     * Create a new, empty instance
     */
    public JsonObject() {
        map = new LinkedHashMap<>();
    }

    /**
     * Create an instance from a Map. The Map is not copied.
     *
     * @param map the map to create the instance from.
     */
    public JsonObject(Map<String, Object> map) {
        this.map = map;
    }


    public static JsonObject from(Object o) throws JsonProcessingException {
        try {
            String value = getObjectMapper().writeValueAsString(o);
            return new JsonObject(value);
        } catch (JsonProcessingException e) {
            log.error("[JsonObject] from object cause{}", e);
            throw e;
        }
    }

    /**
     * Create a JsonObject from the fields of a Java object.
     * Faster than calling `new JsonObject(Json.encode(obj))`.
     *
     * @param obj The object to convert to a JsonObject.
     * @throws IllegalArgumentException if conversion fails due to an incompatible type.
     */
    @SuppressWarnings("unchecked")
    public static JsonObject mapFrom(Object obj) {
        return new JsonObject((Map<String, Object>) getObjectMapper().convertValue(obj, Map.class));
    }

    public <T> T mapTo(Class<T> type) {
        return getObjectMapper().convertValue(map, type);
    }

    private void fromJson(String json) {
        map = decodeValue(json, Map.class);
    }


    /**
     * Get the string value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a String
     */
    public String getString(String key) {
        Objects.requireNonNull(key);
        CharSequence cs = (CharSequence) map.get(key);
        return cs == null ? null : cs.toString();
    }

    /**
     * Get the Integer value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not an Integer
     */
    public Integer getInteger(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;  // Avoids unnecessary unbox/box
        } else {
            return number.intValue();
        }
    }

    /**
     * Get the Long value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a Long
     */
    public Long getLong(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;  // Avoids unnecessary unbox/box
        } else {
            return number.longValue();
        }
    }

    /**
     * Get the Double value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a Double
     */
    public Double getDouble(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number;  // Avoids unnecessary unbox/box
        } else {
            return number.doubleValue();
        }
    }

    /**
     * Get the Float value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a Float
     */
    public Float getFloat(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Float) {
            return (Float) number;  // Avoids unnecessary unbox/box
        } else {
            return number.floatValue();
        }
    }

    /**
     * Get the Boolean value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a Boolean
     */
    public Boolean getBoolean(String key) {
        Objects.requireNonNull(key);
        return (Boolean) map.get(key);
    }


    /**
     * Get the JsonObject value with the specified key
     *
     * @param key the key to return the value for
     * @return the value or null if no value for that key
     * @throws java.lang.ClassCastException if the value is not a JsonObject
     */
    public JsonObject getJsonObject(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        if (val instanceof Map) {
            val = new JsonObject((Map) val);
        }
        return (JsonObject) val;
    }

    /**
     * Get the value with the specified key, as an Object
     *
     * @param key the key to lookup
     * @return the value
     */
    public Object getValue(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        if (val instanceof Map) {
            val = new JsonObject((Map) val);
        } else if (val instanceof List) {
            val = new JsonArray((List) val);
        }
        return val;
    }

    /**
     * Like {@link #getString(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public String getString(String key, String def) {
        Objects.requireNonNull(key);
        CharSequence cs = (CharSequence) map.get(key);
        return cs != null || map.containsKey(key) ? cs == null ? null : cs.toString() : def;
    }

    /**
     * Like {@link #getInteger(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public Integer getInteger(String key, Integer def) {
        Objects.requireNonNull(key);
        Number val = (Number) map.get(key);
        if (val == null) {
            if (map.containsKey(key)) {
                return null;
            } else {
                return def;
            }
        } else if (val instanceof Integer) {
            return (Integer) val;  // Avoids unnecessary unbox/box
        } else {
            return val.intValue();
        }
    }

    /**
     * Like {@link #getLong(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public Long getLong(String key, Long def) {
        Objects.requireNonNull(key);
        Number val = (Number) map.get(key);
        if (val == null) {
            if (map.containsKey(key)) {
                return null;
            } else {
                return def;
            }
        } else if (val instanceof Long) {
            return (Long) val;  // Avoids unnecessary unbox/box
        } else {
            return val.longValue();
        }
    }

    /**
     * Like {@link #getDouble(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public Double getDouble(String key, Double def) {
        Objects.requireNonNull(key);
        Number val = (Number) map.get(key);
        if (val == null) {
            if (map.containsKey(key)) {
                return null;
            } else {
                return def;
            }
        } else if (val instanceof Double) {
            return (Double) val;  // Avoids unnecessary unbox/box
        } else {
            return val.doubleValue();
        }
    }

    /**
     * Like {@link #getFloat(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public Float getFloat(String key, Float def) {
        Objects.requireNonNull(key);
        Number val = (Number) map.get(key);
        if (val == null) {
            if (map.containsKey(key)) {
                return null;
            } else {
                return def;
            }
        } else if (val instanceof Float) {
            return (Float) val;  // Avoids unnecessary unbox/box
        } else {
            return val.floatValue();
        }
    }

    /**
     * Like {@link #getBoolean(String)} but specifying a default value to return if there is no entry.
     *
     * @param key the key to lookup
     * @param def the default value to use if the entry is not present
     * @return the value or {@code def} if no entry present
     */
    public Boolean getBoolean(String key, Boolean def) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        return val != null || map.containsKey(key) ? (Boolean) val : def;
    }

    /**
     * Encode this JSON object as a string.
     *
     * @return the string encoding.
     */
    public String encode() {
        return Json.encode(map);
    }


}
