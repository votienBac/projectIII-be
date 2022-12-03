package vn.noron.data.request;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.model.paging.Pageable;

@Data
@Accessors(chain = true)
public class EsSearchRequest {
    private Long ownerId;
    private String keyword;
    private Pageable pageable;
}
