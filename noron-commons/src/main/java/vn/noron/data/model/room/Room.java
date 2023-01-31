package vn.noron.data.model.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

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
    List<ImageUpload> uploadRoomImages;
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
    Boolean window;
    Boolean waterHeater;
    Object pending;
    Boolean beReported;

    Boolean isVerified;
    GeoCoding.Location location;
    FullAddress fullAddressObject;
    //@JsonProperty("geocodingApi")
    GeoCoding geocodingApi;
    @Data
    @Accessors(chain = true)
    public static class ImageUpload{
        String original;
        String thumbnail;
    }





}
