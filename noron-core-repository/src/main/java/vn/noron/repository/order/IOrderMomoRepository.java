package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.tables.pojos.OrderMomo;
import vn.noron.repository.IBaseRepository;

public interface IOrderMomoRepository extends IBaseRepository<OrderMomo, Long> {
    Single<Integer> updateStateMomo(Long id, String status);

    Single<OrderMomo> getOrderMomoByOrderId(Long orderId);
}
