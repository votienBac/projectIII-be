package vn.noron.api.service.backjob;

import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.noron.commons.repository.room.IRoomRepository;
import vn.noron.core.json.JsonObject;
import vn.noron.data.mapper.room.RoomMapper;
import vn.noron.data.model.room.ApiRoomObject;
import vn.noron.data.model.room.Room;
import vn.noron.data.model.room.RoomOhana;
import vn.noron.service.OkHttpService;

import javax.annotation.PostConstruct;
import java.util.List;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;

@Service
public class BackjobService {
    private final OkHttpService okHttpService;
    private final RoomMapper roomMapper;
    private final IRoomRepository roomRepository;
    @Value("${api-url.get-all-ohana}")
    private String url;
    public BackjobService(OkHttpService okHttpService, RoomMapper roomMapper, IRoomRepository roomRepository) {
        this.okHttpService = okHttpService;
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
    }

    //@PostConstruct
    public Single<List<Room>> getDataFromOhana(){
        return rxSchedulerIo(() -> {
            String body = "{\"search\":{\"location\":{\"lat\":10.855113,\"lng\":106.745268},\"query\":{\"room_location_district\":762,\"room_location\":\"HCM\"}},\"placeDetail\":{\"formatted_address\":\"Thủ Đức\",\"address_components\":{\"name\":\"Thủ Đức\",\"district_index\":true,\"postal\":762,\"locations\":[{\"lat\":10.855113,\"lon\":106.745268}]},\"type\":\"district\",\"roomSearchType\":\"ohana\"},\"room_location\":\"HCM\"}";
            String response = okHttpService.post(url, body);
            ApiRoomObject res = new JsonObject(response).mapTo(ApiRoomObject.class);
            List<RoomOhana> ohanaRoomDatas = res.getData();
            return roomMapper.fromOhana(ohanaRoomDatas);
        });
    }
    //@PostConstruct
    public Single<Boolean> addDataToDB(){
        return getDataFromOhana()
                .map(rooms -> {
                    roomRepository.saveMany(rooms);
                    return true;
                });
    }
}
