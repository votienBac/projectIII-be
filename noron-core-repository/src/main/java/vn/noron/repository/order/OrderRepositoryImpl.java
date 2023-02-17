package vn.noron.repository.order;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.FavoriteRoom;
import vn.noron.data.tables.pojos.Order;
import vn.noron.data.tables.records.OrderRecord;
import vn.noron.repository.AbsRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.*;
import static vn.noron.repository.utils.MysqlUtil.toSortField;

@Repository
public class OrderRepositoryImpl extends AbsRepository<OrderRecord, Order, Long> implements IOrderRepository {
    @Override
    protected TableImpl<OrderRecord> getTable() {
        return ORDER;
    }
    @Override
    public Single<Integer> updateStateOrder(Long id, Boolean isPaid) {
        return rxSchedulerIo(() ->
                dslContext.update(getTable())
                        .set(ORDER.IS_PAID, isPaid)
                        .where(ORDER.ID.eq(id))
                        .returning()
                        .execute());
    }

    @Override
    public Single<Pair<Long, List<Order>>> getByUserId(Long userId, Pageable pageable) {
        return rxSchedulerIo(() -> {
            final @NonNull SelectConditionStep<Record> recordSelectConditionStep = dslContext
                    .select()
                    .from(ORDER)
                    .where(ORDER.USER_ID.eq(userId).and(ORDER.IS_PAID.eq(true)));
            Long total = recordSelectConditionStep.fetchInto(Order.class).stream().count();
            List<Order> favoriteRooms = recordSelectConditionStep
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(Order.class);
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

    @Override
    public Single<Pair<Long, List<Order>>> getAll(Pageable pageable) {
        return rxSchedulerIo(() -> {
            final @NonNull SelectConditionStep<Record> recordSelectConditionStep = dslContext
                    .select()
                    .from(ORDER)
                    .where(ORDER.IS_PAID.eq(true));
            Long total = recordSelectConditionStep.fetchInto(Order.class).stream().count();
            List<Order> favoriteRooms = recordSelectConditionStep
                    .orderBy(toSortField(pageable.getSort(), getTable().fields()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize() + 1)
                    .fetchInto(Order.class);
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
