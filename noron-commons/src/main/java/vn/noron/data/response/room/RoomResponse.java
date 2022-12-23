package vn.noron.data.response.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.model.room.FullAddress;
import vn.noron.data.model.room.GeoCoding;
import vn.noron.data.model.room.Room;
import vn.noron.data.response.user.UserResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoomResponse {
    @JsonProperty("_id")
    String id;
    Long userId;
    String roomName;
    Long roomPrice;
    Double roomArea;
    Double electricPrice;
    Double waterPrice;
    Double parkingFee;
    Double deposit;
    String roomLocation;
    Long roomLocationDistrict;
    Long roomLocationWard;
    String homeNumber;
    String streetName;
    String exactRoomAddress;
    String phoneNumber;
    String roomGender;
    String notes;
    List<Room.ImageUpload> uploadRoomImages;
    Long createdDate;
    Long updatedDate;
    Boolean airConditioner;
    Boolean roomBathroom;
    Boolean parkingSituation;
    Boolean shareHomeAsLandlord;
    Boolean roomRefrigerator;
    Boolean roomWashingMachine;
    Boolean securityGuard;
    Boolean roomBed;
    Boolean roomTivi;
    Boolean roomPetsAllowed;
    Boolean roomCloset;
    Boolean roomKitchen;
    Boolean roomWindow;
    Boolean isTopRoom;
    Long roomView;
    Boolean disabled;
    String roomType;
    Boolean window;
    Boolean waterHeater;
    Object pending;

    Boolean isVerified;
    Boolean isFavoriteRoom = false;
    FullAddress fullAddressObject;

    GeoCoding geocodingApi;
    UserResponse ownerInfo;

    @Data
    @Accessors(chain = true)
    public static class ImageUpload{
        String original;
        String thumbnail;
    }
}
