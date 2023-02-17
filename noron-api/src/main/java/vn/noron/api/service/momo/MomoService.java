package vn.noron.api.service.momo;

import com.google.common.hash.Hashing;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;
import vn.noron.api.service.room.IRoomService;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.config.MomoConfig;
import vn.noron.data.mapper.order.OrderMapper;
import vn.noron.data.request.order_momo.MomoResult;
import vn.noron.data.response.MessageResponse;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.data.tables.pojos.OrderMomo;
import vn.noron.repository.order.IOrderMomoRepository;
import vn.noron.repository.order.IOrderRepository;
import vn.noron.repository.order.IOrderTransactionRepository;

import java.nio.charset.StandardCharsets;

import static vn.noron.data.constant.MomoConstant.getStatus;

@Service
public class MomoService implements IMomoService {
    private final IOrderRepository orderRepository;
    private final MomoConfig momoConfig;
    private final IOrderMomoRepository orderMomoRepository;
    private final IOrderTransactionRepository orderTransactionRepository;
    private final OrderMapper orderMapper;
    private final IUserService userService;
    private final IRoomService roomService;

    public MomoService(IOrderRepository orderRepository,
                       MomoConfig momoConfig,
                       IOrderMomoRepository orderMomoRepository,
                       IOrderTransactionRepository orderTransactionRepository, OrderMapper orderMapper, IUserService userService, IRoomService roomService) {
        this.orderRepository = orderRepository;
        this.momoConfig = momoConfig;
        this.orderMomoRepository = orderMomoRepository;
        this.orderTransactionRepository = orderTransactionRepository;

        this.orderMapper = orderMapper;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public Single<OrderResponse> updateResult(MomoResult result) {
        return orderMomoRepository.getOrderMomoByOrderId(result.getOrderId())
                .flatMap(orderMomo -> returnResult(orderMomo, result));
    }

    @Override
    public Single<OrderResponse> momoReturnResult(MomoResult momoResult) {
        return orderMomoRepository.getOrderMomoByOrderId(momoResult.getOrderId())
                .flatMap(orderMomo -> returnResult(orderMomo, momoResult));
    }

    private Single<OrderResponse> returnResult(OrderMomo orderMomo, MomoResult momoResult) {
        String MomoReturnSignature = generateCreateTransactionSignature(momoResult.getAmount(),
                momoResult.getOrderId(),
                momoResult.getOrderId(),
                orderMomo.getRedirectUrl());
        if (!orderMomo.getSignature().equals(MomoReturnSignature)) {
            return Single.error(new ApiException("invalid_request"));
        }
        if (momoResult.getResultCode() != null) {
            return orderTransactionRepository.getOrderMomoId(momoResult.getOrderId())
                    .flatMap(orderMomoId -> orderMomoRepository.updateStateMomo(orderMomoId, momoResult.getResultCode())
                            .flatMap(integer -> updateOrder(momoResult))
                    );
        }
        return Single.error(new ApiException("payment_failed"));
    }

    private Single<OrderResponse> updateOrder(MomoResult momoResult) {
//        if (momoResult.paymentSuccess()) {
        return orderRepository.updateStateOrder(momoResult.getOrderId(), momoResult.paymentSuccess())
                .flatMap(orderProducts -> orderRepository.findById(momoResult.getOrderId())
                        .map(order -> orderMapper.toResponse(order.get())))
                .flatMap(orderResponse -> Single.zip(roomService.roomDetail(orderResponse.getRoomId(), orderResponse.getUserId()),
                        userService.getDetailUser(orderResponse.getUserId()),
                        ((roomResponse, userResponse) -> {
                            roomService.updateIsPaidRoom(roomResponse.getId(), momoResult.paymentSuccess());
                            return orderResponse.setRoomResponse(roomResponse)
                                    .setOwner(userResponse);
                        })));
//        }
//        return Single.just(new MessageResponse()
//                .setMessage(getStatus().get("status" + momoResult.getResultCode())));

    }

    private String generateCreateTransactionSignature(Long amount, Long orderId, Long requestId, String callBackUrl) {
        String extraData = "";
        String secret = momoConfig.getSecretKey();
        String key = "accessKey=" + momoConfig.getAccessKey() +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + momoConfig.getIpnUrl() +
                "&orderId=" + orderId +
                "&orderInfo=" + momoConfig.getOrderInfo() +
                "&partnerCode=" + momoConfig.getPartnerCode() +
                "&redirectUrl=" + callBackUrl +
                "&requestId=" + requestId +
                "&requestType=" + momoConfig.getRequestType();
        return Hashing.hmacSha256(secret.getBytes()).hashString(key, StandardCharsets.UTF_8).toString();
    }


}


