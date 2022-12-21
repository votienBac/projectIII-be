package vn.noron.data.request.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.model.room.FullAddress;
import vn.noron.data.model.room.GeoCoding;
import vn.noron.data.model.room.Room;


import java.util.List;

@Data
@Accessors(chain = true)
public class CreateRoomRequest {
    @JsonIgnore
    Long userId;
    @JsonIgnore
    Boolean isAdmin;
    String roomName;
    Long roomPrice;
    Double roomArea;
    Double electricPrice;
    Double waterPrice;
    Double parkingFee;
    Double deposit; //thang dat coc
    String roomLocation;
    Long roomLocationDistrict;
    Long roomLocationWard;
    String homeNumber;
    String streetName;
    String exactRoomAddress;
    String phoneNumber;
    Boolean roomIsShared;
    String roomGender;
    String notes;
    List<Room.ImageUpload> uploadRoomImages;
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
    Boolean isFeaturedRoom;
    String roomType;
    Boolean window;
    Boolean waterHeater;
    FullAddress fullAddressObject;
    GeoCoding geocodingApi;

}
