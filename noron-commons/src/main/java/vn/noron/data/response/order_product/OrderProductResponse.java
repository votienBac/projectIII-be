package vn.noron.data.response.order_product;

import lombok.Data;

@Data
public class OrderProductResponse {
    private Integer reportId;
    private String  reportTitle;
    private Double  salePrice;
    private Integer productId;
    private String  productTitle;
    private Double  finalPrice;
    private String  productType;
    private String  discount;
}
