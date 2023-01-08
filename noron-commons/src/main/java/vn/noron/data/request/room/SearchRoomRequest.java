package vn.noron.data.request.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.model.room.GeoCoding;

import java.util.List;

@Data
@Accessors(chain = true)
public class SearchRoomRequest {
    Search search;
    Filter filter;
    String roomLocation;
    @JsonIgnore
    Long userId;

    @Data
    @Accessors(chain = true)
    public static class Filter {
        Price price;
        List<String> utilities;
        List<String> roomType;
        List<String> roomGender;


    }

    @Data
    @Accessors(chain = true)
    public static class Price {
        Double from;
        Double to;

    }

    @Data
    @Accessors(chain = true)
    public static class Search {
        Location location;
        Query query;

    }

    @Data
    @Accessors(chain = true)
    public static class Location {
        Double lng;
        Double lat;
        Double maxDistance;

    }

    @Data
    @Accessors(chain = true)
    public static class Query {
        Integer roomLocationDistrict;
        String roomLocation;

    }

}
