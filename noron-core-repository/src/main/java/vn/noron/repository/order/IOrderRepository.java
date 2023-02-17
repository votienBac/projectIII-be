package vn.noron.repository.order;

import io.reactivex.rxjava3.core.Single;
import org.apache.commons.lang3.tuple.Pair;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.tables.pojos.Order;
import vn.noron.repository.IBaseRepository;

import java.util.List;
import java.util.Map;

public interface IOrderRepository extends IBaseRepository<Order, Long> {

    Single<Integer> updateStateOrder(Long id, Boolean isPaid);
    Single<Pair<Long,List<Order>>> getByUserId(Long userId, Pageable pageable);
    Single<Pair<Long,List<Order>>> getAll(Pageable pageable);
}
