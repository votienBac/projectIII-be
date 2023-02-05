package vn.noron.data.response.order_momo;

import lombok.Data;
import vn.noron.data.response.order.OrderResponse;

import java.time.LocalDateTime;

@Data
public class ShortOrderMomoResponse {
    private Long totalPrice;
    private String status;
    private LocalDateTime createAt;
    private String content;
    private OrderResponse orderResponse;
}
