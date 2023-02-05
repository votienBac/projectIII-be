package vn.noron.data.constant;

import java.util.HashMap;
import java.util.Map;

public class MomoConstant {
    public static String momoUrl = "https://test-payment.momo.vn/v2/gateway/api/create";
    public static String status0    = "Giao dịch thành công.";
    public static String status9000 = "Giao dịch đã được xác nhận thành công.";
    public static String status8000 = "Giao dịch đang ở trạng thái cần được người dùng xác nhận thanh toán lại.";
    public static String status7000 = "Giao dịch đang được xử lý.";
    public static String status1000 = "Giao dịch đã được khởi tạo, chờ người dùng xác nhận thanh toán.";
    public static String status11   = "Truy cập bị từ chối.";
    public static String status12 = "Phiên bản API không được hỗ trợ cho yêu cầu này.";
    public static String status13 = "Xác thực doanh nghiệp thất bại.";
    public static String status20 = "Yêu cầu sai định dạng.";
    public static String status22 = "Số tiền giao dịch không hợp lệ.";
    public static String status40 = "RequestId bị trùng.";
    public static String status41 = "OrderId bị trùng.";
    public static String status42 = "OrderId không hợp lệ hoặc không được tìm thấy.";
    public static String status43 = "Yêu cầu bị từ chối vì xung đột trong quá trình xử lý giao dịch.";
    public static String status1001 = "Giao dịch thanh toán thất bại do tài khoản người dùng không đủ tiền.";
    public static String status1002 = "Giao dịch bị từ chối do nhà phát hành tài khoản thanh toán.";
    public static String status1003 = "Giao dịch bị đã bị hủy.";
    public static String status1004 = "Giao dịch thất bại do số tiền thanh toán vượt quá hạn mức thanh toán của người dùng.";
    public static String status1005 = "Giao dịch thất bại do url hoặc QR code đã hết hạn.";
    public static String status1006 = "Giao dịch thất bại do người dùng đã từ chối xác nhận thanh toán.";
    public static String status1007 = "Giao dịch bị từ chối vì tài khoản người dùng đang ở trạng thái tạm khóa.";
    public static String status1026 = "public static String status";
    public static String status1030 = "Đơn hàng thanh toán thất bại do thông tin không hợp lệ.";
    public static String status1080 = "Giao dịch hoàn tiền bị từ chối. Giao dịch thanh toán ban đầu không được tìm thấy.";
    public static String status1081 = "Giao dịch hoàn tiền bị từ chối. Giao dịch thanh toán ban đầu có thể đã được hoàn.";
    public static String status2001 = "Giao dịch thất bại do sai thông tin liên kết.";
    public static String status2007 = "Giao dịch thất bại do liên kết hiện đang bị tạm khóa.";
    public static String status3001 = "Liên kết thất bại do người dùng từ chối xác nhận.";
    public static String status3002 = "Liên kết bị từ chối do không thỏa quy tắc liên kết.";
    public static String status3003 = "Hủy liên kết bị từ chối do đã vượt quá số lần hủy.";
    public static String status3004 = "Liên kết này không thể hủy do có giao dịch đang chờ xử lý.";
    public static String status4001 = "Giao dịch bị hạn chế do người dùng chưa hoàn tất xác thực tài khoản.";
    public static String status4010 = "Quá trình xác minh OTP thất bại.";
    public static String status4011 = "OTP chưa được gửi hoặc hết hạn.";
    public static String status4100 = "Giao dịch thất bại do người dùng không đăng nhập thành công.";
    public static String status4015 = "Quá trình xác minh 3DS thất bại.";
    public static String status10 = "Hệ thống đang được bảo trì.";
    public static String status99 = "Lỗi không xác định.";
    public static Map<String,String> status = new HashMap<>();

    public static Map<String, String> getStatus() {
        status.put("status0",status0);
        status.put("status9000",status9000);
        status.put("status8000",status8000);
        status.put("status7000",status7000);
        status.put("status1000",status1000);
        status.put("status11",status11);
        status.put("status12",status12);
        status.put("status13",status13);
        status.put("status20",status20);
        status.put("status22",status22);
        status.put("status40",status40);
        status.put("status41",status41);
        status.put("status42",status42);
        status.put("status43",status43);
        status.put("status1001",status1001);
        status.put("status1002",status1002);
        status.put("status1003",status1003);
        status.put("status1004",status1004);
        status.put("status1005",status1005);
        status.put("status1006",status1006);
        status.put("status1007",status1007);
        status.put("status1026",status1026);
        status.put("status1030",status1030);
        status.put("status1080",status1080);
        status.put("status1081",status1081);
        status.put("status2001",status2001);
        status.put("status2007",status2007);
        status.put("status3001",status3001);
        status.put("status3002",status3002);
        status.put("status3003",status3003);
        status.put("status3004",status3004);
        status.put("status4001",status4001);
        status.put("status4010",status4010);
        status.put("status4011",status4011);
        status.put("status4100",status4100);
        status.put("status4015",status4015);
        status.put("status10",status10);
        status.put("status99",status99);
        return status;
    }
}
