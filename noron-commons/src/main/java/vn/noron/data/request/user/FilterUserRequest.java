package vn.noron.data.request.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class FilterUserRequest {
    private List<Long> roleIds;
}
