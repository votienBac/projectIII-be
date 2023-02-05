package vn.noron.data.mapper.order;

import org.mapstruct.Mapper;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.data.tables.pojos.Order;

@Mapper(componentModel = "spring")
public abstract class OrderMapper  {

//    @Autowired
//    private UserMapper userMapper;
//
    public abstract OrderResponse toResponse(Order order);
//
//    @AfterMapping
//    protected void map(@MappingTarget OrderResponse orderResponse, Order order, @Context User owner, @Context Coupon coupon) {
//        orderResponse.setOwner(userMapper.toResponse(owner));
//        orderResponse.setCoupon(coupon);
//    }
//
//    public List<OrderResponse> toListResponse(List<Order> orders, Map<Integer, Coupon> couponMap, Map<Long, User> userMap,
//                                              Map<Integer, List<OrderProduct>> mapProduct) {
//        return orders.stream()
//                .map(order -> toResponse(order, userMap.get(order.getOwnerId()),
//                        couponMap.get(order.getCouponId()), mapProduct.get(order.getId())))
//                .collect(toList());
//    }
//
//    public Order toPojo(OrderRequest orderRequest, Long loggedUserId) {
//        Order order = toPojo(orderRequest);
//        order.setOwnerId(loggedUserId);
//        return order;
//    }
}