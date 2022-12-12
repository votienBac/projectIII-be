package vn.noron.data.request.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.pojo.room.FullAddress;
import vn.noron.data.pojo.room.GeoCoding;

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
    Boolean vacantRoom; //phong trong hay ko
    Boolean stayedPlace;
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
    Boolean numberVacanciesAvailableInRoom;
    String roomGender;
    String notes;
    List<String> uploadRoomImages;
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
    String closedHour;
    String openedHour;
    FullAddress fullAddressObject;
    GeoCoding geocodingApi;

}
