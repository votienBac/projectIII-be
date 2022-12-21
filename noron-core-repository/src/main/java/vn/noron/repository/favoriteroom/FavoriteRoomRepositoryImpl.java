package vn.noron.repository.favoriteroom;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.data.tables.records.FavoriteRoomRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZonedDateTime.now;
import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.*;
import static vn.noron.repository.utils.MysqlUtil.toSortField;

@Repository
public class FavoriteRoomRepositoryImpl extends AbsRepository<FavoriteRoomRecord, FavoriteRoom, Long> implements IFavoriteRoomRepository {
    @Override
    protected TableImpl<FavoriteRoomRecord> getTable() {
        return FAVORITE_ROOM;
    }

    @Override
    public Single<Integer> deleteFromFavorite(Long userId, String roomId) {
        return rxSchedulerIo(() -> dslContext.delete(getTable())
                .where(FAVORITE_ROOM.USER_ID.eq(userId).and(FAVORITE_ROOM.ROOM_ID.eq(roomId)))
                .execute());
    }

    @Override
    public Single<Integer> deleteByRoomId(String roomId) {
        return rxSchedulerIo(() -> dslContext.delete(getTable())
                .where(FAVORITE_ROOM.ROOM_ID.eq(roomId))
                .execute());
    }

    @Override
    public Single<Pair<Long, List<FavoriteRoom>>> getPageable(Long userId, Pageable pageable) {
        return rxSchedulerIo(() -> {
            final @NonNull SelectConditionStep<Record> recordSelectConditionStep = dslContext
                    .select()
                    .from(FAVORITE_ROOM)
                    .where(FAVORITE_ROOM.USER_ID.eq(userId));
            Long total = recordSelectConditionStep.fetchInto(FavoriteRoom.class).stream().count();
            List<FavoriteRoom> favoriteRooms = recordSelectConditionStep
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(FavoriteRoom.class);
            if (favoriteRooms.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return Pair.of(total,
                        favoriteRooms.stream().limit(pageable.getPageSize()).collect(Collectors.toList()));
            } else {
                pageable.setLoadMoreAble(false);
                return Pair.of(total, favoriteRooms);
            }
        });
    }

}
