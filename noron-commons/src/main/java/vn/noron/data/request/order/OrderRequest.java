package vn.noron.data.request.order;


import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.model.room.Room;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderRequest {
    private Double total;
//    private String  email;
//    private String  phoneNumber;
//    @NotBlank(message = "address is not empty")
//    private String  address;
//    private List<OrderProduct> orderProducts;
    private Long userId;
    private Room room;
}
