package vn.noron.data.pojo.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.core.json.Json;

@Data
@Accessors(chain = true)
public class GeoCoding {
    Location location;
    @JsonProperty("view_port")
    ViewPort viewPort;

    @JsonProperty("location_type")
    String locationType;
    @Data
    @Accessors(chain = true)
    public static class Location{
        Double lat;
        Double lng;
    }
    @Data
    @Accessors(chain = true)
    public static class ViewPort{
        Location northeast;
        Location southwest;
    }



}
