package vn.noron.api.service.momo;


import io.reactivex.rxjava3.core.Single;
import vn.noron.data.request.order_momo.MomoResult;
import vn.noron.data.response.MessageResponse;
import vn.noron.data.response.order.OrderResponse;

public interface IMomoService {
    Single<OrderResponse> updateResult (MomoResult result);

    Single<OrderResponse> momoReturnResult (MomoResult momoResult);
}
