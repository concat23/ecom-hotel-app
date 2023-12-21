package dev.concat.vab.ecomhotelappbackend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestMethod;
    protected Map<?,?> data;


    public HttpResponse(int statusCode, HttpStatus status, String message, String requestMethod) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.requestMethod = requestMethod;
    }

    public HttpResponse(String timeStamp, int statusCode, HttpStatus status, String reason, String message, String developerMessage, String path, String requestMethod, Map<?, ?> data) {
        this.timeStamp = timeStamp;
        this.statusCode = statusCode;
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.developerMessage = developerMessage;
        this.path = path;
        this.requestMethod = requestMethod;
        this.data = data;
    }


}
