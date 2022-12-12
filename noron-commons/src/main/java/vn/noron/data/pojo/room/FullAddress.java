package vn.noron.data.pojo.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FullAddress {
    City city;
    District district;
    @JsonProperty("street_name")
    String streetName;
    @JsonProperty("house_number")
    String houseNumber;

    @Data
    @Accessors(chain = true)
    public class City{
        String code;
        String text;
    }

    @Data
    @Accessors(chain = true)
    public class District{
        String code;
        String text;
        String cityCode;
    }
}
