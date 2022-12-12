package vn.noron.commons.repository.room;

import vn.noron.commons.repository.IMongoRepository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;

import java.util.List;

public interface IRoomRepository extends IMongoRepository<Room> {
    //Room createRoom(Room room);

    void updateRoom(Room room);
    List<Room> getByIds(List<String> id, String roomType);
    List<Room> getRoomByStatus(Boolean isPending);
    List<Room> searchPageable(String keyword, Pageable pageable);
    long countSearchPageable(String keyword);
}
