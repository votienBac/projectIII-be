package vn.noron.repository.reportroom;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.ReportRoom;
import vn.noron.data.tables.pojos.User;
import vn.noron.data.tables.records.ReportRoomRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.*;
import static vn.noron.data.Tables.USER;
import static vn.noron.repository.utils.MysqlUtil.buildSearchQueries;
import static vn.noron.repository.utils.MysqlUtil.toSortField;

@Repository
public class ReportRoomRepositoryImpl extends AbsRepository<ReportRoomRecord, ReportRoom, Long> implements IReportRoomRepository {
    @Override
    protected TableImpl<ReportRoomRecord> getTable() {
        return REPORT_ROOM;
    }
    @Override
    public Single<Pair<Long, List<ReportRoom>>> getByRoomId(String roomId, Pageable pageable) {
        return rxSchedulerIo(() -> {
            final @NonNull SelectConditionStep<Record> recordSelectConditionStep = dslContext
                    .select()
                    .from(REPORT_ROOM)
                    .where(REPORT_ROOM.ROOM_ID.eq(roomId));
            Long total = recordSelectConditionStep.fetchInto(ReportRoom.class).stream().count();
            List<ReportRoom> reportRooms = recordSelectConditionStep
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(ReportRoom.class);
            if (reportRooms.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return Pair.of(total,
                        reportRooms.stream().limit(pageable.getPageSize()).collect(Collectors.toList()));
            } else {
                pageable.setLoadMoreAble(false);
                return Pair.of(total, reportRooms);
            }
        });
    }
    @Override
    public Single<List<ReportRoom>> getAllByRoomIds(List<String> roomIds) {
        return rxSchedulerIo(() -> dslContext
                    .select()
                    .from(REPORT_ROOM)
                    .where(REPORT_ROOM.ROOM_ID.in(roomIds))
                    .fetchInto(ReportRoom.class));
    }

    @Override
    public Single<Long> filterAndSearchCount(String keyword) {
        var conditionVar = new Object() {
            Condition condition = buildSearchQueries(getTable(), keyword);
        };
        return rxSchedulerIo(() -> dslContext.selectCount()
                .from(REPORT_ROOM)
                .where(conditionVar.condition)
                .fetchOneInto(Long.class));
    }
    @Override
    public Single<List<ReportRoom>> filterAndPageable(String keyword, Pageable pageable) {
        var conditionVar = new Object() {
            Condition condition = buildSearchQueries(getTable(), keyword);
        };
        return rxSchedulerIo(() -> {
            final List<ReportRoom> reportRooms = dslContext.select(REPORT_ROOM.fields())
                    .from(REPORT_ROOM)
                    .where(conditionVar.condition)
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(ReportRoom.class);
            if (reportRooms.size() > pageable.getPageSize()) {
                pageable.setLoadMoreAble(true);
                return reportRooms.stream().limit(pageable.getPageSize()).collect(Collectors.toList());
            } else {
                pageable.setLoadMoreAble(false);
                return reportRooms;
            }
        });
    }

}
