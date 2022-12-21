package vn.noron.data.model.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullAddress {
    City city;
    District district;
    @JsonProperty("streetName")
    String streetName;
    @JsonProperty("houseNumber")
    String houseNumber;
    @Data
    @Accessors(chain = true)
    public static class City{
        String code;
        String text;
    }

    @Data
    @Accessors(chain = true)
    public static class District{
        String code;
        String text;
        @JsonProperty("cityCode")
        String cityCode;
    }

}
