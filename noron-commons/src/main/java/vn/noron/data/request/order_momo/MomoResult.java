package vn.noron.data.request.order_momo;

import lombok.Data;

@Data
public class MomoResult {
    private String partnerCode;
    private Long orderId;
    private Long requestId;
    private Long amount;
    private String orderInfo;
    private String orderType;
    private String transId;
    private String resultCode;
    private String message;
    private String payType;
    private String responseTime;
    private String extraData;
    private String signature;

    public boolean paymentSuccess(){
        if(this.getResultCode().equals("0") || this.getResultCode().equals("9000")){
            return true;
        }
        else return false;
    }
}
