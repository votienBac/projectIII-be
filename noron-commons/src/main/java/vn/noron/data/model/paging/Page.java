package vn.noron.data.model.paging;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Page<T> {
    private String key;
    private Long total;
    private Integer page;
    private List<T> items;
    private Boolean loadMoreAble;
    private Boolean preLoadAble;

    public Page() {
    }

    public Page(Long total, Integer page, List<T> items) {
        this.page = page;
        this.total = total;
        this.items = items;
    }

    public Page(Long total, Pageable pageable, List<T> items) {
        this.total = total;
        this.page = pageable.getPage();
        this.items = items;
        this.loadMoreAble = total != null &&
                (total.intValue() > (pageable.getOffset() + pageable.getPageSize()));
    }

    public Page(Pageable pageable, List<T> items) {
        this.items = items;
        this.total = pageable.getTotal();
        this.page = pageable.getPage();
        this.loadMoreAble = pageable.getTotal() > (pageable.getOffset() + pageable.getPageSize());
    }
}
