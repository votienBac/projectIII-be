package vn.noron.data.request.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.utils.time.CustomOffsetDateTimeDeSerializer;
import vn.noron.utils.time.CustomOffsetDateTimeSerializer;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class FilterResetPasswordRequest {
    private List<Long> roleIds;
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeSerializer.class)
    private OffsetDateTime timeStart;
    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeSerializer.class)
    private OffsetDateTime timeEnd;
}
