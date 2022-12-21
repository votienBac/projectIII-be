package vn.noron.data.constant.room;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum RoomType {

    NOT_SHARED(1, "NotShared"),
    SHARED(2, "Shared"),
    DORMITORY(3, "Dormitory"),
    HOUSE(4, "House"),
    APARTMENT(5, "Apartment");
    private Integer id;
    private String type;

    RoomType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    public static List<String> getAll(){
        return Arrays.stream(RoomType.values())
                .map(RoomType::getType)
                .collect(Collectors.toList());
    }
}
