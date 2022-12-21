package vn.noron.data.model.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCoding {


    Location location;
    ViewPort viewport;

    String locationType;


    @Data
    @Accessors(chain = true)
    public static class Location {
        Double lat;
        Double lng;
    }

    @Data
    @Accessors(chain = true)
    public static class ViewPort {
        Location northeast;
        Location southwest;
    }
}
