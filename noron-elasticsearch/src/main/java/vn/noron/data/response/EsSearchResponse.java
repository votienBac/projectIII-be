package vn.noron.data.response;

import lombok.Data;
import lombok.experimental.Accessors;
import vn.noron.data.TDocument;

import java.util.List;

@Data
@Accessors(chain = true)
public class EsSearchResponse<T extends TDocument> {
    private Long total;
    private List<T> items;
    private String scrollId;
}
