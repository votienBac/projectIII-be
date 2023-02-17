package vn.noron.api.service.ordermomo;

import com.google.common.hash.Hashing;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;
import vn.noron.api.service.room.IRoomService;
import vn.noron.api.service.user.IUserService;
import vn.noron.apiconfig.config.exception.ApiException;
import vn.noron.config.MomoConfig;
import vn.noron.core.json.JsonObject;
import vn.noron.data.mapper.order.OrderMapper;
import vn.noron.data.mapper.ordermomo.OrderMomoMapper;
import vn.noron.data.model.paging.Pageable;
import vn.noron.data.request.order.OrderRequest;
import vn.noron.data.response.momo.MomoResponse;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.data.response.order_momo.OrderMomoResponse;
import vn.noron.data.response.room.RoomResponse;
import vn.noron.data.response.user.UserResponse;
import vn.noron.data.tables.pojos.*;
import vn.noron.repository.order.IOrderMomoRepository;
import vn.noron.repository.order.IOrderRepository;
import vn.noron.repository.order.IOrderTransactionRepository;
import vn.noron.repository.user.IUserRepository;
import vn.noron.service.OkHttpService;
import vn.noron.utils.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static vn.noron.core.template.RxTemplate.rxSchedulerIo;
import static vn.noron.data.Tables.FAVORITE_ROOM;
import static vn.noron.data.constant.MomoConstant.*;
import static vn.noron.repository.utils.MysqlUtil.toSortField;
import static vn.noron.utils.CollectionUtils.*;


@Service
public class OrderMomoService implements IOrderMomoService {
    private final OrderMomoMapper orderMomoMapper;
    private final MomoConfig momoConfig;
    private final IOrderRepository orderRepository;
    private final IOrderTransactionRepository orderTransactionRepository;
    private final OrderMapper orderMapper;
    private final IOrderMomoRepository orderMomoRepository;
    private final IRoomService roomService;
    private final IUserService userService;
    private final IUserRepository userRepository;

    public OrderMomoService(OrderMomoMapper orderMomoMapper,
                            MomoConfig momoConfig,
                            IOrderRepository orderRepository,
                            IOrderTransactionRepository orderTransactionRepository,
                            OrderMapper orderMapper,
                            IOrderMomoRepository orderMomoRepository,
                            IRoomService roomService, IUserService userService,
                            IUserRepository userRepository) {
        this.orderMomoMapper = orderMomoMapper;
        this.momoConfig = momoConfig;
        this.orderRepository = orderRepository;
        this.orderTransactionRepository = orderTransactionRepository;
        this.orderMapper = orderMapper;
        this.orderMomoRepository = orderMomoRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public Single<OrderMomoResponse> saveOrderToDb(OrderRequest orderRequest, String callBackUrl) {

        if (orderRequest.getUserId() == null)
            return Single.error(new ApiException("you haven't permission"));
        if (orderRequest.getRoom().getIsPaid()) {
            return Single.error(new ApiException("room is deposited"));
        }
        if (!orderRequest.getRoom().getIsVerified()) {
            return Single.error(new ApiException("room isn't belong to admin"));
        }
        if (!orderRequest.getTotal().equals(orderRequest.getRoom().getDeposit()))
            return Single.error(new ApiException("invalid order"));

        Order order = new Order()
                .setUserId(orderRequest.getUserId())
                .setRoomId(orderRequest.getRoom().getId())
                .setIsPaid(false)
                .setTotal(orderRequest.getTotal())
                .setCreatedAt(OffsetDateTime.now());
        return userRepository.findById(orderRequest.getUserId())
                .flatMap(user -> {
                    return user.map(value -> orderRepository.insertReturning(order)
                            .flatMap(orderReturn -> this.checkOrderId(order.getTotal(),
                                    orderReturn.get().getId(),
                                    orderRequest,
                                    value,
                                    callBackUrl))).orElseGet(() -> Single.error(new ApiException("User not found!")));
                });
    }

    @Override
    public Single<MomoResponse> createOrder(OrderRequest orderRequest, String callBackUrl) {
        return saveOrderToDb(orderRequest, callBackUrl)
                .flatMap(OrderMomoService::createOrderFromMomo);
    }

    @Override
    public Single<List<OrderResponse>> getOrderOfUser(Long userId, Pageable pageable) {
        return orderRepository.getByUserId(userId, pageable)
                .flatMap(longListPair -> {
                    pageable.setTotal(longListPair.getLeft());
                    return mapToResponse(longListPair.getRight());
                });


    }

    @Override
    public Single<List<OrderResponse>> getOrder(Pageable pageable) {
        return orderRepository.getAll(pageable)
                .flatMap(longListPair -> {
                    pageable.setTotal(longListPair.getLeft());
                    return mapToResponse(longListPair.getRight());
                });
    }

    public Single<List<OrderResponse>> mapToResponse(List<Order> orders) {
        List<String> roomIds = extractField(orders, Order::getRoomId);
        List<Long> userIds = extractField(orders, Order::getUserId);
        return Single.zip(roomService.getByIDS(roomIds),
                userService.getByIds(userIds),
                (roomResponses, userResponses) -> {
                    Map<String, RoomResponse> roomMap = collectToMap(roomResponses, RoomResponse::getId);
                    Map<Long, UserResponse> userMap = collectToMap(userResponses, UserResponse::getId);
                    return orderMapper.toResponses(orders)
                            .stream()
                            .map(orderResponse -> orderResponse.setRoomResponse(roomMap.get(orderResponse.getRoomId()))
                                    .setOwner(userMap.get(orderResponse.getUserId())))
                            .collect(Collectors.toList());
                });
    }

    public static Single<MomoResponse> createOrderFromMomo(OrderMomoResponse orderMomoResponse) {
        return rxSchedulerIo(() -> {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            OkHttpService okHttpService1 = new OkHttpService(client);
            String url = momoUrl;
            String json = JsonObject.mapFrom(orderMomoResponse).encode();
            String post = okHttpService1.post(url, json);
            return new JsonObject(post).mapTo(MomoResponse.class);
        });
    }


    private Single<OrderMomoResponse> checkOrderId(Double amount,
                                                   Long orderId,
                                                   OrderRequest orderRequest,
                                                   User user,
                                                   String callBackUrl) {
        if (orderId == -1) return Single.error(new ApiException("orders_purchased"));
        return saveOrderMomo(amount, orderId, orderRequest, user, callBackUrl)
                .flatMap(orderMomo -> saveOrderMomoDetail(orderRequest, user, orderMomo));
    }

    private Single<OrderMomoResponse> saveOrderMomoDetail(OrderRequest orderRequest,
                                                          User user,
                                                          OrderMomo orderMomo) {
//        List<OrderDetailMomo> orderDetailMomos = orderDetailMomoMapper.toPojos(orderRequest.getOrderProducts(), orderMomo.getId());
        OrderTransaction orderTransaction = toOrderTransaction(orderMomo.getOrderId(), orderMomo.getId(), "Momo");
        return Single.zip(orderTransactionRepository.insertReturning(orderTransaction),
                userService.getDetailUser(orderRequest.getUserId()),
                ((orderTransaction1, userResponse) -> orderMomoMapper.toResponse(orderMomo)
                        .setUserInfo(userResponse)));
    }

    private Single<OrderMomo> saveOrderMomo(Double amount,
                                            Long orderId,
                                            OrderRequest orderRequest,
                                            User user,
                                            String callBackUrl) {
        if (callBackUrl == null) callBackUrl = momoConfig.getRedirectUrl();
        String signature = generateCreateTransactionSignature(amount.longValue(), orderId, orderId.toString(), callBackUrl);
        OrderMomo orderMomo = orderMomoMapper.toPojo(orderRequest, user.getId(), orderId, signature, callBackUrl);
        return orderMomoRepository.insertReturning(orderMomo)
                .map(Optional::get);
    }

    private OrderTransaction toOrderTransaction(Long orderId, Long transactionId, String type) {
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setOrderId(orderId);
        orderTransaction.setOrderMomoId(transactionId);
        orderTransaction.setType(type);
        return orderTransaction;
    }

    private String generateCreateTransactionSignature(Long amount, Long orderId, String requestId, String callBackUrl) {
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