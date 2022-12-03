package vn.noron.utils.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import vn.noron.utils.TimeUtilCommon;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;

public class CustomOffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
    public static final String SDF_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public CustomOffsetDateTimeSerializer() { }

    @SneakyThrows
    public void serialize(OffsetDateTime date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.setLenient(false);
        String formattedDate = dateFormat.format(TimeUtilCommon.convertOffsetDateTimeToDate(date));
        jsonGenerator.writeString(formattedDate);
    }
}
