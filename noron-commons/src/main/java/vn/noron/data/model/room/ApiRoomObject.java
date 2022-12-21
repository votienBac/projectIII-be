package vn.noron.data.model.room;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ApiRoomObject {
    String success;
    List<RoomOhana> data;
    @Data
    @Accessors(chain = true)
    public static class Dataa{
        List<Room> data;
    }
}
