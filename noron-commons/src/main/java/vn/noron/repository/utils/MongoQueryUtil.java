package vn.noron.repository.utils;

import com.google.common.base.CaseFormat;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import org.bson.conversions.Bson;
import vn.noron.data.model.Filter;
import vn.noron.data.model.paging.Order;
import vn.noron.data.model.paging.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static vn.noron.data.constant.GenericFieldConstant._ID;

public class MongoQueryUtil {
    private MongoQueryUtil() {
    }


    public static Bson sort(Pageable pageable) {
        if (pageable.getSort() == null) {
            pageable.setSort(Collections.emptyList());
        }

        return Sorts.orderBy(
                pageable.getSort()
                        .stream()
                        .map(MongoQueryUtil::byDirection)
                        .collect(toList()));
    }

    public static Bson sort(List<Order> sort) {
        if (sort == null) {
            sort = Collections.emptyList();
        }
        return Sorts.orderBy(sort
                .stream()
                .map(MongoQueryUtil::byDirection)
                .collect(toList()));
    }

    private static Bson byDirection(Order order) {

        if (order.getDirection().equalsIgnoreCase(Order.Direction.asc.name()) || "false".equalsIgnoreCase(order.getDirection())) {
            return Sorts.ascending(order.getProperty());
        } else {
            return Sorts.descending(order.getProperty());
        }
    }

    public static <T> Bson buildFilterQueries(List<Filter> fieldFilters, Class<T> tClass) {
        final Set<String> fields = Arrays.stream(tClass.getDeclaredFields())
                .map(field -> snakeCase(field.getName()))
                .collect(Collectors.toSet());

        if (isEmpty(fieldFilters)) return Filters.exists(_ID, true);
        final List<Bson> collect = fieldFilters.stream()
                .filter(filter -> fields.contains(filter.getName()))
                .map(MongoQueryUtil::buildCondition)
                .collect(Collectors.toList());
        return Filters.and(collect);
    }

    private static String snakeCase(String input) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input);
    }


    public static Bson buildOrFilterQueries(List<Filter> fieldFilters) {
        if (isEmpty(fieldFilters)) return Filters.exists(_ID, true);
        final List<Bson> collect = fieldFilters.stream()
                .map(MongoQueryUtil::buildCondition)
                .collect(Collectors.toList());
        return Filters.or(collect);
    }

    private static Bson buildCondition(Filter filter) {

        Operator operator = Operator.from(filter.getOperation());
        switch (operator) {
            case IN: {
                return Filters.in(filter.getName(), (List) filter.getValue());
            }
            case NIN: {
                return Filters.nin(filter.getName(), (List) filter.getValue());
            }
            case EQUAL: {
                return Filters.eq(filter.getName(), filter.getValue());
            }
            case LIKE: {
                if (filter.getValue() == null) return Filters.exists(_ID, true);
                return Filters.eq(filter.getName(), filter.getValue());
            }
            case NOT_EQUAL: {
                return Filters.ne(filter.getName(), filter.getValue());
            }
            case GREATER_THAN: {
                return Filters.gt(filter.getName(), filter.getValue());
            }
            case LESS_THAN: {
                return Filters.lt(filter.getName(), filter.getValue());
            }
            case GREATER_THAN_OR_EQUAL: {
                return Filters.gte(filter.getName(), filter.getValue());
            }
            case LESS_THAN_OR_EQUAL: {
                return Filters.lte(filter.getName(), filter.getValue());
            }
            default:
                return Filters.eq(filter.getName(), filter.getValue());
        }
    }
}
