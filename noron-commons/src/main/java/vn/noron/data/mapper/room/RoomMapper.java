package vn.noron.data.mapper.room;

import org.mapstruct.*;
import vn.noron.data.model.room.GeoCoding;
import vn.noron.data.model.room.Room;
import vn.noron.data.model.room.RoomOhana;
import vn.noron.data.request.room.CreateRoomRequest;
import vn.noron.data.request.room.UpdateRoomRequest;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {
    public abstract Room toPOJO(CreateRoomRequest source);

    @AfterMapping
    public void afterToPOJO(@MappingTarget Room target, CreateRoomRequest source) {
        target.setDisabled(false);
        target.setCreatedDate(Instant.now().toEpochMilli());
        if(source.getIsAdmin()) {
            target.setPending(false);
            target.setIsVerified(true);
        }
        else{
            target.setIsVerified(false);
            target.setPending(true);
        }
        target.setBeReported(false);
        target.setIsPaid(false);
    }

    public static void main(String[] args) {
        long x = Instant.now().toEpochMilli();
        long y = Instant.now().getEpochSecond();
        System.out.println(1);
    }
    public abstract Room toPOJO(UpdateRoomRequest source);

    @AfterMapping
    public void afterUpdateToPOJO(@MappingTarget Room target, UpdateRoomRequest source) {
        target.setUpdatedDate(Instant.now().toEpochMilli());
    }

    @Named(value = "fromOhana")
    @Mapping(target = "uploadRoomImages", ignore = true)
    public abstract Room convertOhanaData(RoomOhana source);
    @IterableMapping(qualifiedByName = "fromOhana")
    public abstract List<Room> fromOhana(List<RoomOhana> ohanas);

    @AfterMapping
    public void afterConvertOhanaData(@MappingTarget Room target, RoomOhana source) {
        target.setUploadRoomImages(source.getUploadRoomImages().stream()
                .map(s -> new Room.ImageUpload()
                        .setOriginal(s)
                        .setThumbnail(s))
                .collect(Collectors.toList()));
        target.setUserId(1l);
        target.setIsVerified(true);
        target.setBeReported(false);
        target.setIsPaid(false);
        target.setPending(false);
        target.setLocation(new GeoCoding.Location().setLng(source.getGeocodingApi().getLocation().getLng())
                .setLat(source.getGeocodingApi().getLocation().getLat()));
    }

    @Named(value = "toResponse")
    public abstract RoomResponse toResponse(Room source);
    @IterableMapping(qualifiedByName = "toResponse")
    public abstract List<RoomResponse> roomResponses(List<Room> rooms);

//    @AfterMapping
//    public void afterToResponse(@MappingTarget RoomResponse target, Room source, UserResponse userResponse) {
//        target.setOwnerInfo(userResponse);
//    }
}
