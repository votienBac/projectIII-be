package vn.noron.repository.utils;

import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.UpdatableRecordImpl;
import vn.noron.core.json.JsonArray;
import vn.noron.data.model.Filter;
import vn.noron.data.model.paging.Order;
import vn.noron.data.model.query.Operator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createLong;
import static org.jooq.impl.DSL.trueCondition;
import static vn.noron.core.json.JsonObject.mapFrom;
import static vn.noron.data.model.paging.Order.Direction.asc;
import static vn.noron.data.model.query.Operator.IN;
import static vn.noron.data.model.query.Operator.NIN;
import static vn.noron.utils.TimeUtil.longToLocalDateTime;

public class MysqlUtil {
    private MysqlUtil() {
    }

    public static <T extends UpdatableRecordImpl<T>> Map<Field<?>, Object>
    recordToUpdateQueries(T record, Object o, TableField<T, ?>... ignoreFields) {
        record.from(o);
        Map<Field<?>, Object> values = new HashMap<>();
        for (Field<?> f : record.fields()) {
            if (record.getValue(f) != null) {
                if (Arrays.stream(ignoreFields).noneMatch(f::equals))
                    values.put(f, record.getValue(f));
            }
        }
        return values;
    }

    public static <T extends TableRecordImpl<T>> Map<Field<?>, Object>
    toInsertQueries(T record, Object o) {
        record.from(o);
        Map<Field<?>, Object> values = new HashMap<>();
        for (Field<?> f : record.fields()) {
            if (record.getValue(f) != null) {
                values.put(f, record.getValue(f));
            }
        }
        return values;
    }

    public static <T extends TableRecordImpl<T>> Map<Field<?>, Object>
    toInsertQueries(T record, Object o, List<String> fields) {
        record.from(o);
        final HashSet<String> fieldSet = new HashSet<>(fields);
        Map<Field<?>, Object> values = new HashMap<>();
        for (Field<?> f : record.fields()) {
            if (fieldSet.contains(f.getName())) {
                values.put(f, record.getValue(f));
            }
        }
        return values;
    }

    public static List<SortField<Object>> toSortField(List<Order> orderProperties, Field<?>[] fields) {
        if (isEmpty(orderProperties)) return new ArrayList<>();
        Set<String> fieldNames = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toSet());
        return orderProperties
                .stream()
                .filter(order -> fieldNames.contains(order.getProperty()))
                .map(order -> {
                    if (order.getDirection().equals(asc.name()))
                        return DSL.field(order.getProperty()).asc();
                    else return DSL.field(order.getProperty()).desc();
                })
                .collect(Collectors.toList());
    }

    public static <R extends Record> Condition buildFilterQueries(TableImpl<R> table, List<Filter> fieldFilters) {
        if (isEmpty(fieldFilters)) return DSL.noCondition();
        final Condition[] condition = {DSL.noCondition()};
        fieldFilters
                .forEach(fieldFilter -> {
                    final Field field = table.field(fieldFilter.getName());
                    if (field != null) {
                        final Object valueByClass = castValueByClass(fieldFilter.getOperation(), fieldFilter.getValue(), field.getType());
                        if (valueByClass != null) {
                            condition[0] = condition[0].and(buildCondition(fieldFilter.getOperation(), field, valueByClass));

                        } else {
                            condition[0] = condition[0].and(field.isNull());
                        }
                    }
                });
        return condition[0];
    }

    public static <R extends Record> Condition buildOrFilterQueries(TableImpl<R> table, List<Filter> fieldFilters) {
        if (isEmpty(fieldFilters)) return DSL.noCondition();
        return getCondition(table, fieldFilters, new HashSet<>());
    }

    public static <R extends Record> Condition buildOrFilterQueries(TableImpl<R> table, List<Filter> fieldFilters, Field<?>... fieldIgnore) {
        if (isEmpty(fieldFilters)) return DSL.noCondition();
        Set<Field<?>> fieldIgnoreSet = Arrays.stream(fieldIgnore).collect(Collectors.toSet());
        return getCondition(table, fieldFilters, fieldIgnoreSet);
    }

    private static <R extends Record> Condition getCondition(TableImpl<R> table, List<Filter> fieldFilters, Set<Field<?>> fieldIgnoreSet) {
        final Condition[] condition = {DSL.noCondition()};
        fieldFilters
                .stream().filter(filter -> !fieldIgnoreSet.contains(table.field(filter.getName())))
                .forEach(fieldFilter -> {
                    final Field field = table.field(fieldFilter.getName());
                    if (field != null) {
                        final Object valueByClass = castValueByClass(fieldFilter.getOperation(), fieldFilter.getValue(), field.getType());
                        if (valueByClass != null) {
                            condition[0] = condition[0].or(buildCondition(fieldFilter.getOperation(), field, valueByClass));
                        } else {
                            condition[0] = condition[0].or(field.isNull());
                        }
                    }
                });
        return condition[0];
    }

    public static <R extends Record> Condition buildSearchQueries(TableImpl<R> table, String keyword) {
        if (isEmpty(keyword)) return DSL.noCondition();
        final Condition[] condition = {DSL.noCondition()};
        Arrays.stream(table.fields())
                .filter(field -> String.class.isAssignableFrom(field.getType()) || Long.class.isAssignableFrom(field.getType()))
                .forEach(field -> condition[0] = condition[0].or(field.likeRegex(keyword)));
        return condition[0];
    }

    private static Condition buildCondition(String operation, Field<Object> field, Object value) {

        var operator = Operator.from(operation);
        switch (operator) {
            case IN: {
                return field.in(value);
            }
            case NIN: {
                return field.notIn(value);
            }
            case EQUAL: {
                return field.eq(value);
            }
            case LIKE: {
                if (value == null) return trueCondition();
                return field.likeRegex(value.toString());
            }
            case NOT_EQUAL: {
                return field.ne(value);
            }
            case GREATER_THAN: {
                return field.gt(value);
            }
            case LESS_THAN: {
                return field.lt(value);
            }
            case GREATER_THAN_OR_EQUAL: {
                return field.greaterOrEqual(value);
            }
            case LESS_THAN_OR_EQUAL: {
                return field.lessOrEqual(value);
            }
            default:
                return field.eq(value);
        }
    }

    public static <V> Object castValueByClass(String operation, Object value, Class<V> classValue) {
        if (operation != null && Arrays.asList(IN.getOperator(), NIN.getOperator()).contains(operation)) {
            final JsonArray array = new JsonArray(value.toString());
            return array.stream()
                    .map(object -> castValueByClass(null, object, classValue))
                    .collect(Collectors.toList());
        }
        try {
            if (classValue.getSimpleName().equalsIgnoreCase(LocalDateTime.class.getSimpleName()))
                return longToLocalDateTime(createLong(value.toString()));
            if (classValue.getSimpleName().equalsIgnoreCase(Timestamp.class.getSimpleName()))
                return new Timestamp(createLong(value.toString()));
            if (String.class.isAssignableFrom(classValue)) return value;
            return mapFrom(value).mapTo(classValue);
        } catch (Exception e) {
            return null;
        }
    }
}
