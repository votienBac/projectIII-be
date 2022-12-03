package vn.noron.apiconfig.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class DfResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public DfResponse() {
        code = 0;
        message = "OK";
    }

    public DfResponse(String message) {
        this.code = 0;
        this.message = message;
    }

    public DfResponse(String message, T result) {
        this.code = 0;
        this.message = message;
        this.result = result;
    }

    public DfResponse(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseEntity<DfResponse<T>> okEntity(T body) {
        return ResponseEntity.ok(ok(body));
    }

    public static <T> DfResponse<T> ok(T body) {
        DfResponse<T> response = new DfResponse<>();
        response.setResult(body);
        return response;
    }

    public static DfResponse<String> badRequest(String message) {
        return new DfResponse<>(message, HttpStatus.BAD_REQUEST.value());
    }

    public static  <T> DfResponse<T>  inValidException(T body){
        DfResponse<T> response = new DfResponse<>();
        response.setCode(422);
        response.setMessage("error");
        response.setResult(body);
        return response;
    }
}
