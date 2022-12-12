package vn.noron.data.model.room;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.pojo.room.FullAddress;
import vn.noron.data.pojo.room.GeoCoding;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    @JsonProperty("_id")
    String id;
    Long userId;
    String ownerName;
    String roomName;
    Long roomPrice;
    Double roomArea;
    Boolean vacantRoom;
    Boolean stayedPlace;
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
    Boolean roomIsShared;
    Boolean numberVacanciesAvailableInRoom;
    String roomGender;
    String notes;
    List<String> uploadRoomImages;
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
    Boolean isFeaturedRoom;
    String roomType;
    String availableStatus;
    String availableStatusDate;
    Boolean window;
    Boolean waterHeater;
    Boolean pending;
    String closedHour;
    String openedHour;
    Boolean isVerified;
    FullAddress fullAddressObject;
    GeoCoding geocodingApi;


}
