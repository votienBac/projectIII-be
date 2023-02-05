package vn.noron.api.service.ordermomo;


import io.reactivex.rxjava3.core.Single;
import vn.noron.data.request.order.OrderRequest;
import vn.noron.data.response.momo.MomoResponse;
import vn.noron.data.response.order_momo.OrderMomoResponse;

public interface IOrderMomoService {
    Single<OrderMomoResponse> saveOrderToDb(OrderRequest orderRequest, String callBackUrl);

//    Single<OrderMomoResponse>
    Single<MomoResponse> createOrder(OrderRequest orderRequest, String callBackUrl);
}
