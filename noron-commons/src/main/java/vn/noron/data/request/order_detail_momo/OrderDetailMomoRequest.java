package vn.noron.data.request.order_detail_momo;

import lombok.Data;

@Data
public class OrderDetailMomoRequest {
    private String name;
    private String description;
    private String category;
    private String imageUrl;
    private String manufacturer;
    private Long price;
    private String currency;
    private Integer quantity;
    private String unit;
    private Long totalPrice;
    private Long taxAmount;
}
