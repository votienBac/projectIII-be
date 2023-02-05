package vn.noron.data.mapper.ordermomo;


import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import vn.noron.config.MomoConfig;
import vn.noron.data.request.order.OrderRequest;
import vn.noron.data.response.order_momo.OrderMomoResponse;
import vn.noron.data.tables.pojos.OrderMomo;

@Mapper(componentModel = "spring")
public abstract class OrderMomoMapper {
    @Autowired
    protected MomoConfig momoConfig;


    @Mapping(target = "amount", source = "orderRequest.total")
    public abstract OrderMomo toPojo(OrderRequest orderRequest, Long userId, Long orderId, String signature,
                                     String callBackUrl);

    @AfterMapping
    protected void map(@MappingTarget OrderMomo orderMomo,
                       Long userId,
                       Long orderId, String signature,
                       String callBackUrl) {
        orderMomo.setUserId(userId);
        orderMomo.setOrderId(orderId);
        orderMomo.setPartnerName(momoConfig.getPartnerName());
        orderMomo.setPartnerCode(momoConfig.getPartnerCode());
        orderMomo.setRequestId(orderId.toString());
        orderMomo.setOrderInfo(momoConfig.getOrderInfo());
        orderMomo.setRedirectUrl(callBackUrl);
        orderMomo.setIpnUrl(momoConfig.getIpnUrl());
        orderMomo.setRequestType(momoConfig.getRequestType());
        orderMomo.setSignature(signature);
        orderMomo.setExtraData("");
    }


    public abstract OrderMomoResponse toResponse(OrderMomo orderMomo);

//    @Mapping(target = "totalPrice",source = "orderMomo.amount")
//    @Mapping(target = "content",source = "orderMomo.orderInfo")
//    @Mapping(target = "orderResponse",expression = "java(orderMapper.toResponse(order))")
//    public abstract ShortOrderMomoResponse toResponse(OrderMomo orderMomo,
//                                                      @Context Order order);
//    @AfterMapping
//    protected void map(@MappingTarget ShortOrderMomoResponse shortOrderMomoResponse){
//        shortOrderMomoResponse.setStatus
//                ( MomoConstant.getStatus().get("status"+shortOrderMomoResponse.getStatus()));
//    }
}
