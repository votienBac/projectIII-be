package vn.noron.repository.reportroom;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.model.room.Room;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.data.tables.pojos.ReportRoom;
import vn.noron.repository.IBaseRepository;

import java.util.List;

public interface IReportRoomRepository extends IBaseRepository<ReportRoom, Long> {
    Single<Pair<Long, List<ReportRoom>>> getByRoomId(String roomId, Pageable pageable);
    Single<List<ReportRoom>> getAllByRoomIds(List<String> roomIds);

    Single<Long> filterAndSearchCount(String keyword);

    Single<List<ReportRoom>> filterAndPageable(String keyword, Pageable pageable);
}
