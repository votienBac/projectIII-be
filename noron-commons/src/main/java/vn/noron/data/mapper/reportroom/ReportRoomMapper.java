package vn.noron.data.mapper.reportroom;

import org.mapstruct.*;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
import vn.noron.data.request.reportroom.ReportRoomRequest;
import vn.noron.data.response.reportroom.ReportRoomResponse;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.data.tables.pojos.ReportRoom;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReportRoomMapper {
    public abstract ReportRoom toPOJO(ReportRoomRequest source);

    @AfterMapping
    public void afterToPOJO(@MappingTarget ReportRoom target, ReportRoomRequest source) {
        target.setCreatedAt(OffsetDateTime.now());
    }

    @Named(value = "toResponse")
    public abstract ReportRoomResponse toResponse(ReportRoom source);
    @IterableMapping(qualifiedByName = "toResponse")
    public abstract List<ReportRoomResponse> roomResponses(List<ReportRoom> sources);
}
