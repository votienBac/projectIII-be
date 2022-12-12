package vn.noron.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static <T> T getValueIfNullDefault(T value, T defaultValue) {
        if (value == null) return defaultValue;
        return value;
    }

//    public static <T> T ifNull(T value, T defaultValue) {
//        if (value == null) return defaultValue;
//        return value;
//    }

    public static <T> void ifNull(T value, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    public static <T> void ifNull(T value, T defaultValue, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        } else {
            action.accept(defaultValue);
        }
    }

    public static <T, K> void ifNull(T condition, K ifNull, K ifNotNull, Consumer<K> action) {
        if (condition == null) {
            action.accept(ifNull);
        } else {
            action.accept(ifNotNull);
        }
    }

    public static void ifEmpty(String value, String defaultValue, Consumer<String> action) {
        if (isNotEmpty(value)) {
            action.accept(value);
        } else {
            action.accept(defaultValue);
        }
    }

    public static Boolean byteToBool(Byte b) {
        if (b == null) return false;
        return b == 1;
    }

    public static <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    public static boolean equalLists(List<String> one, List<String> two) {
        if (one == null && two == null) {
            return true;
        }

        if (one == null || two == null || one.size() != two.size()) {
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        one = new ArrayList<String>(one);
        two = new ArrayList<String>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

    public static Boolean isEmptyList(List<? extends Object> list) {
        return list == null || list.isEmpty();
    }

    public static <V, K> List<V> getValues(Map<K, V> map, List<K> keys) {
        if (keys == null || keys.isEmpty() || map == null || map.isEmpty()) {
            return new ArrayList<>();
        }
        return keys.stream().map(map::get).filter(Objects::nonNull).collect(toList());
    }

    public static <T> List<T> unionList(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();

        list.addAll(list1);
        list.addAll(list2);

        return list.stream().distinct().collect(toList());
    }

    public static Object invokeMethodByName(Object obj, String methodName, Object... args) {
        java.lang.reflect.Method method;
        try {
            method = obj.getClass().getMethod(methodName);
            if (args == null || args.length == 0)
                return method.invoke(obj);
            return method.invoke(obj, args);
        } catch (SecurityException | NoSuchMethodException e) {
            logger.error("[method " + methodName + "not found] cause: {}", e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error("[fail to invoke method] cause: {}", e.getMessage());
        }
        return null;
    }
}
