package vn.noron.data.mapper.order;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import vn.noron.data.response.order.OrderResponse;
import vn.noron.data.tables.pojos.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper  {

//    @Autowired
//    private UserMapper userMapper;
//
    @Named("toResponse")
    public abstract OrderResponse toResponse(Order order);
    @IterableMapping(qualifiedByName = "toResponse")
    public abstract List<OrderResponse> toResponses(List<Order> orders);
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
