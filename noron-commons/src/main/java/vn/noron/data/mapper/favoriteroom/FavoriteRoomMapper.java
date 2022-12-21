package vn.noron.data.mapper.favoriteroom;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.favoriteroom.FavoriteRoomRequest;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.tables.pojos.FavoriteRoom;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public abstract class FavoriteRoomMapper {
    public abstract FavoriteRoom toPOJO(FavoriteRoomRequest source);

    @AfterMapping
    public void afterToPOJO(@MappingTarget FavoriteRoom target, FavoriteRoomRequest source) {
        target.setCreatedAt(OffsetDateTime.now());
    }
}
