package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.tables.pojos.OrderMomo;
import vn.noron.data.tables.records.OrderMomoRecord;
import vn.noron.repository.AbsRepository;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.ORDER_MOMO;

@Repository
public class OrderMomoRepositoryImpl extends AbsRepository<OrderMomoRecord, OrderMomo, Long> implements IOrderMomoRepository{
    @Override
    protected TableImpl<OrderMomoRecord> getTable() {
        return ORDER_MOMO;
    }
    @Override
    public Single<Integer> updateStateMomo(Long id, String status) {
        return rxSchedulerIo(() ->
                dslContext.update(ORDER_MOMO)
                        .set(ORDER_MOMO.STATUS, status)
                        .where(ORDER_MOMO.ID.eq(id))
                        .returning()
                        .execute());

    }

    @Override
    public Single<OrderMomo> getOrderMomoByOrderId (Long orderId) {
        return rxSchedulerIo(() ->
                dslContext.selectFrom(ORDER_MOMO)
                        .where(ORDER_MOMO.ORDER_ID.eq(orderId))
                        .fetchOneInto(OrderMomo.class));
    }
}
