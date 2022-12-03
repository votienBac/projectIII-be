package vn.noron.apiconfig.config.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.noron.apiconfig.model.DfResponse;
import vn.noron.core.exception.DBException;
import vn.noron.core.log.Logger;


import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    private static final Logger logger = Logger.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleException(MissingServletRequestParameterException ex, WebRequest request) {
        String name = ex.getParameterName();
        return generateExceptionResponse(BAD_REQUEST.value(), "Missing " + name);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handlerAccessServiceException(ApiException ex, WebRequest request) {
        if (ex.getCode() == 422)
            return new ResponseEntity<>(DfResponse.inValidException(ex.getData()), UNPROCESSABLE_ENTITY);
        return generateExceptionResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(DBException.class)
    public ResponseEntity<Object> handlerDBException(DBException ex, WebRequest request) {
        return generateExceptionResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return generateExceptionResponse(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
    }


//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<Object> handlerException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
//        ;
//        return generateExceptionResponse(METHOD_NOT_ALLOWED.value(), ex.getMessage());
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handlerNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        if (ex.getCause() instanceof JsonMappingException && ex.getCause().getCause() instanceof ApiException) {
            return handlerAccessServiceException(new ApiException("time_start","cant parse dat, format: yyyy/MM/dd HH:mm:ss"), request);
        }
        return generateExceptionResponse(NOT_ACCEPTABLE.value(), NOT_ACCEPTABLE.getReasonPhrase());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handlerAuthenticationException(Exception ex, WebRequest request) {
        return generateExceptionResponse(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase());
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<Object> handlerThrowableException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return generateExceptionResponse(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = null;
                    try {
                        fieldName = ((FieldError) error).getField();
                    } catch (Exception exception) {
                        fieldName = "fields";
                    }

                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(DfResponse.inValidException(errors), UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> generateExceptionResponse(Integer statusCode, String message) {
        if (statusCode == INTERNAL_SERVER_ERROR.value()) logger.error(message);
        HttpStatus resolve = resolve(statusCode);
        if (resolve == null) resolve = BAD_REQUEST;
        return ResponseEntity.status(resolve)
                .body(new HashMap<String, Object>() {{
                    put("message", message);
                    put("code", statusCode);
                }});
    }
}
