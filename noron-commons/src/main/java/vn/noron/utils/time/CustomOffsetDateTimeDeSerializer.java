package vn.noron.utils.time;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.utils.TimeUtilCommon;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class CustomOffsetDateTimeDeSerializer extends JsonDeserializer<OffsetDateTime> {
    public static final String SDF_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public CustomOffsetDateTimeDeSerializer() {
    }

    @SneakyThrows
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        String dateString = jsonParser.getText();
        if (StringUtils.isEmpty(dateString.strip())) {
            return null;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            dateFormat.setLenient(false);
            Date date = null;

            try {
                date = dateFormat.parse(dateString);
                return TimeUtilCommon.convertDateToOffsetDateTime(date);
            } catch (Exception var7) {
                throw new ApiException("cant parse dat, format: yyyy/MM/dd HH:mm:ss, received: " + dateString, UNPROCESSABLE_ENTITY.value());
            }
        }
    }
}
