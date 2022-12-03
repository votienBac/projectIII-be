package vn.noron.logger.service;

import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.core.log.Logger;
import vn.noron.data.log.NoronLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class ClientLogConsumer {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final ClientLogHandlerFactory clientLogHandlerFactory;

    public ClientLogConsumer(ClientLogHandlerFactory clientLogHandlerFactory) {
        this.clientLogHandlerFactory = clientLogHandlerFactory;
    }

    public void handle(List<NoronLog> logs) {
        final Map<String, List<NoronLog>> keyLogs = logs.stream()
                .collect(groupingBy(this::getKey));
        clientLogHandlerFactory
                .getClientLogHandlers(keyLogs.keySet())
                .entrySet()
                .parallelStream()
                .forEach(entry -> {
                    final List<IClientLogHandler> handlers = entry.getValue();
                    final List<NoronLog> noronLogs = keyLogs.getOrDefault(entry.getKey(), new ArrayList<>());
                    final List<Single<Boolean>> singleList = handlers.parallelStream()
                            .map(handler -> handler.handle(noronLogs))
                            .collect(Collectors.toList());
                    if (!singleList.isEmpty())
                        Single.zip(singleList, objects -> objects)
                                .subscribe(result -> logger.info("success"),
                                        throwable -> logger.error("[CLIENT-LOG] cause {}", throwable));
                });
    }

    private String getKey(NoronLog noronLog) {
        String key = noronLog.getActionType();
        return isNotEmpty(noronLog.getObjectType()) ? noronLog.getObjectType() + "-" + key : key;
    }
}
