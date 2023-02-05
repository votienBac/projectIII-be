package vn.noron.data.response.order_momo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.response.user.UserResponse;

@Data
@Accessors(chain = true)
@JsonNaming
public class OrderMomoResponse {
    private String partnerCode;
    private String partnerName;
    private String storeId;
    private String requestId;
    private Long amount;
    private Integer orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String requestType;
    private String extraData;
    private boolean autoCapture;
    private String signature;
    private String status;
    private UserResponse userInfo;
//    @JsonProperty("items")
//    private List<OrderDetailMomoResponse> orderDetailMomoes;
}
