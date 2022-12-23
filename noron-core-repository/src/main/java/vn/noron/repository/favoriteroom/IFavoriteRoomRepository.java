package vn.noron.repository.favoriteroom;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.repository.IBaseRepository;

import java.util.List;

public interface IFavoriteRoomRepository extends IBaseRepository<FavoriteRoom, Long> {
    Single<Integer> deleteFromFavorite(Long userId, String roomId);
    Single<Integer> deleteByRoomId(String roomId);

    Single<List<FavoriteRoom>> getByUserIdAndRoomIds(Long userId, List<String> roomIds);
    Single<List<FavoriteRoom>> getByUserId(Long userId);
    Single<Pair<Long, List<FavoriteRoom>>> getPageable(Long userId, Pageable pageable);
}
