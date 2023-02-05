package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.tables.pojos.OrderTransaction;
import vn.noron.repository.IBaseRepository;

public interface IOrderTransactionRepository extends IBaseRepository<OrderTransaction, Long> {


    Single<Long> getOrderMomoId(Long orderId);
}
