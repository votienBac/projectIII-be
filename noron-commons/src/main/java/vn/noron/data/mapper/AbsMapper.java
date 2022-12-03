package vn.noron.data.mapper;

import vn.noron.utils.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static vn.noron.utils.TimeUtil.localDateToLong;

public abstract class AbsMapper {

    protected Long map(LocalDate localDate) {
        return localDateToLong(localDate);
    }

    protected Long map(LocalDateTime localDateTime) {
        return TimeUtil.localDateTimeToLong(localDateTime);
    }

}
