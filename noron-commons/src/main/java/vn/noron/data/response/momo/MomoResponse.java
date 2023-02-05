package vn.noron.data.response.momo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming
public class MomoResponse {
    private String partnerCode;
    private Integer orderId;
    private Integer requestId;
    private Long amount;
    private String responseTime;
    private String message;
    private String resultCode;
    private String payUrl;
}
