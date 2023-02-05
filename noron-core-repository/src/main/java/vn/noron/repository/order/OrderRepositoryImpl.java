package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.tables.pojos.Order;
import vn.noron.data.tables.records.OrderRecord;
import vn.noron.repository.AbsRepository;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.ORDER;

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
}
