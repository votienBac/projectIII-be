package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.tables.pojos.OrderTransaction;
import vn.noron.data.tables.records.OrderTransactionRecord;
import vn.noron.repository.AbsRepository;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.ORDER_TRANSACTION;
@Repository
public class OrderTransactionImplRepository extends AbsRepository<OrderTransactionRecord, OrderTransaction, Long> implements IOrderTransactionRepository {
    @Override
    protected TableImpl<OrderTransactionRecord> getTable() {
        return ORDER_TRANSACTION;
    }

    @Override
    public Single<Long> getOrderMomoId(Long orderId){
        return rxSchedulerIo(()->
                dslContext.select(ORDER_TRANSACTION.ORDER_MOMO_ID)
                        .from(ORDER_TRANSACTION)
                        .where(ORDER_TRANSACTION.ORDER_ID.eq(orderId))
                        .fetchOneInto(Long.class)
        );
    }
}
