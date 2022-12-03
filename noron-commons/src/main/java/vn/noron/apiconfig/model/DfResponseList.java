package vn.noron.apiconfig.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.noron.data.model.paging.Pageable;

import java.util.List;

@Data
public class DfResponseList<T> {
    private Integer code;
    private String message;

    private int total;
    private List<T> result;

    public DfResponseList() {
        code = 0;
        message = "OK";
    }

    public DfResponseList(String message) {
        this.code = 0;
        this.message = message;
    }

    public DfResponseList(String message, List<T> result, int total) {
        this.code = 0;
        this.message = message;
        this.total = total;
        this.result = result;
    }

    public DfResponseList(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseEntity<DfResponseList<T>> okEntity(List<T> body, Pageable pageable) {
        return ResponseEntity.ok(ok(body,(int) pageable.getTotal()));
    }

    public static <T> DfResponseList<T> ok(List<T> body, int total) {
        return new DfResponseList<>("OK", body, total);
    }

    public static DfResponse<String> badRequest(String message) {
        return new DfResponse<>(message, HttpStatus.BAD_REQUEST.value());
    }
}
