package vn.noron.logger.service.impl;

import io.reactivex.rxjava3.core.Single;
import vn.noron.config.annotation.Identity;
import vn.noron.core.log.Logger;
import vn.noron.data.log.NoronLog;
import vn.noron.logger.service.IClientLogHandler;

import java.util.List;

import static vn.noron.data.constant.KafkaKeyConstant.HEART_BIT;


@Identity(HEART_BIT)
public class HeartbitLogService implements IClientLogHandler {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public Single<Boolean> handle(List<NoronLog> logs) {
        logger.info("[NUM-MESSAGE] {}", logs);
        // todo
        return Single.just(Boolean.TRUE);
    }
}
