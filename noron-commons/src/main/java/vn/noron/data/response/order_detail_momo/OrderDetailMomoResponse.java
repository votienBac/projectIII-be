package vn.noron.data.response.order_detail_momo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming
public class OrderDetailMomoResponse {
    private String name;
    private String description;
//    private String category;
    private String imageUrl;
    private String manufacturer;
    private Long price;
    private String currency;
//    private Integer quantity;
//    private String unit;
    private Long totalPrice;
//    private Long taxAmount;
}
