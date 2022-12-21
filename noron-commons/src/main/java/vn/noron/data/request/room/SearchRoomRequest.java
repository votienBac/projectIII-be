package vn.noron.data.request.room;

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
        GeoCoding.Location location;
        Query query;

    }

    @Data
    @Accessors(chain = true)
    public static class Query {
        Integer roomLocationDistrict;
        String roomLocation;

    }

}
