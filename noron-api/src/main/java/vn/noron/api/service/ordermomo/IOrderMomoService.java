package vn.noron.api.service.ordermomo;


import io.reactivex.rxjava3.core.Single;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.order.OrderRequest;
import vn.noron.data.response.momo.MomoResponse;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.data.response.order_momo.OrderMomoResponse;

import java.util.List;

public interface IOrderMomoService {
    Single<OrderMomoResponse> saveOrderToDb(OrderRequest orderRequest, String callBackUrl);

//    Single<OrderMomoResponse>
    Single<MomoResponse> createOrder(OrderRequest orderRequest, String callBackUrl);
    Single<List<OrderResponse>> getOrderOfUser(Long userId, Pageable pageable);
    Single<List<OrderResponse>> getOrder(Pageable pageable);
}
