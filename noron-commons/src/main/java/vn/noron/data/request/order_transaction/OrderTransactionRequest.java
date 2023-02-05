package vn.noron.data.request.order_transaction;

import lombok.Data;

@Data
public class OrderTransactionRequest {
    private Integer orderId;
    private Integer momoId;
}
