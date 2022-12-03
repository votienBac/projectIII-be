package vn.noron.logger.service;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.log.NoronLog;

import java.util.List;

public interface IClientLogHandler {
    Single<Boolean> handle(List<NoronLog> logs);
}
