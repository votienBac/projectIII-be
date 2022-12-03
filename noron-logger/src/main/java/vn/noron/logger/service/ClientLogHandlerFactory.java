package vn.noron.logger.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import vn.noron.config.annotation.Identity;

import java.util.*;

import static java.util.stream.Collectors.*;

@Component
public class ClientLogHandlerFactory {
    private final Map<String, List<IClientLogHandler>> CLIENT_LOG_HANDLER_MAP;

    public ClientLogHandlerFactory(List<IClientLogHandler> clientLogHandlers) {
        CLIENT_LOG_HANDLER_MAP = clientLogHandlers.stream()
                .filter(handle -> handle.getClass().isAnnotationPresent(Identity.class))
                .flatMap(handle -> Arrays.stream(handle.getClass().getAnnotation(Identity.class)
                        .value()).map(identity -> Pair.of(identity, handle)))
                .collect(groupingBy(Pair::getLeft, mapping(Pair::getRight, toList())));
    }

    public List<IClientLogHandler> getClientLogHandler(String identity) {
        return CLIENT_LOG_HANDLER_MAP.getOrDefault(identity, new ArrayList<>());
    }

    public Map<String, List<IClientLogHandler>> getClientLogHandlers(Collection<String> identities) {
        return identities.stream()
                .collect(toMap(
                        key -> key,
                        key -> CLIENT_LOG_HANDLER_MAP.getOrDefault(key, new ArrayList<>())));
    }
}
