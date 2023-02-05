package vn.noron.data.response.order;


import lombok.Data;
import vn.noron.data.tables.pojos.Order;
import vn.noron.data.tables.pojos.User;

import java.util.List;

@Data
public class SaveOrderElement {
    private Order order;
//    private List<OrderProduct> orderProducts;
    private User user;

}
