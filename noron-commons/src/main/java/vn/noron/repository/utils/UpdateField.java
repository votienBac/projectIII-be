package vn.noron.repository.utils;

import org.jooq.Field;

import java.util.HashMap;
import java.util.Map;

public class UpdateField {
    private Map<Field, Object> fieldValueMap;

    public UpdateField add(Field field, Object value) {
        getFieldValueMap().put(field, value);
        return this;
    }

    public Map<Field, Object> getFieldValueMap() {
        if (fieldValueMap == null) fieldValueMap = new HashMap<>();
        return fieldValueMap;
    }
}
