package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import vn.noron.data.tables.pojos.Order;
import vn.noron.repository.IBaseRepository;

import java.util.List;

public interface IOrderRepository extends IBaseRepository<Order, Long> {

    Single<Integer> updateStateOrder(Long id, Boolean isPaid);
}
