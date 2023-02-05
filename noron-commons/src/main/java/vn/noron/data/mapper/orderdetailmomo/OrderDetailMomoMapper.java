package vn.noron.data.mapper.orderdetailmomo;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderDetailMomoMapper {
//    @Mapping(target = "name", source = "orderProduct.productTitle")
//    @Mapping(target = "category", source = "orderProduct.reportTitle")
//    @Mapping(target = "price", source = "orderProduct.salePrice")
//    @Mapping(target = "unit", source = "orderProduct.productType")
//    @Mapping(target = "totalPrice", source = "orderProduct.finalPrice")
//    public abstract OrderDetailMomo toPojo(OrderProduct orderProduct, Integer orderMomoId);
//
//    @AfterMapping
//    protected void map(@MappingTarget OrderDetailMomo orderDetailMomo) {
//        orderDetailMomo.setQuantity(1);
//        orderDetailMomo.setTaxAmount(0.0);
//        orderDetailMomo.setCurrency("VND");
//        orderDetailMomo.setCreatedAt(LocalDateTime.now());
//    }
//
//    public List<OrderDetailMomo> toPojos(List<OrderProduct> orderProducts, Integer orderMomoId) {
//        return orderProducts.stream()
//                .map(orderProduct -> toPojo(orderProduct, orderMomoId))
//                .collect(Collectors.toList());
//    }

}
