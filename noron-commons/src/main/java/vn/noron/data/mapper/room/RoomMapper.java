package vn.noron.data.mapper.room;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.noron.data.model.room.Room;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;

import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {
    public abstract Room toPOJO(CreateRoomRequest source);

    @AfterMapping
    public void afterToPOJO(@MappingTarget Room target, CreateRoomRequest source) {
        target.setDisabled(false);
        target.setCreatedDate(Instant.now().getEpochSecond());
        if(source.getIsAdmin()) {
            target.setPending(false);
            target.setIsVerified(true);
        }
        else{
            target.setIsVerified(false);
            target.setPending(true);
        }
    }
    public abstract Room toPOJO(UpdateRoomRequest source);

    @AfterMapping
    public void afterUpdateToPOJO(@MappingTarget Room target, UpdateRoomRequest source) {
        target.setUpdatedDate(Instant.now().getEpochSecond());
    }

}
