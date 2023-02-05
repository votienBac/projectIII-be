package vn.noron.data.mapper.ordertransaction;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderTransactionMapper{
//    public abstract OrderTransaction toPojo(Integer orderId, Integer transactionId, String type);
//
//    @AfterMapping
//    protected void afterMapping(@MappingTarget OrderTransaction orderTransaction) {
//        orderTransaction.setCreatedAt(LocalDateTime.now());
//    }
}