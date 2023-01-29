package vn.noron.data.model.room;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CountResponse {
    private Long _id;
    private Integer count;

}
