package bangdori.api.comm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResponse {

    @JsonProperty("STATUS")
    private String status;

    @JsonProperty("MESSAGE")
    private Map<String, Object> message = new HashMap<>();

    @JsonProperty("RESULT")
    private Map<String, Object> result = new HashMap<>();

    // Value Constants
    public static final String VALUE_STATUS_SUCCESS = "200";
    public static final String VALUE_STATUS_INVALID = "400";
    public static final String VALUE_STATUS_AUTH_TOKEN_ERROR = "401";
    public static final String VALUE_STATUS_FORBIDDEN = "403";
    public static final String VALUE_STATUS_NOT_FOUND = "404";
    public static final String VALUE_STATUS_METHOD_NOT_ALLOWED = "405";
    public static final String VALUE_STATUS_RUNTIME_ERROR = "500";

    // 성공 응답
    public ApiResponse success() {
        this.status = VALUE_STATUS_SUCCESS;
        setMessage("요청이 성공적으로 처리되었습니다.");
        return this;
    }

    // 실패 응답
    public ApiResponse fail(String code, String reason) {
        this.status = code;
        setMessage(reason);
        return this;
    }

    public ApiResponse setMessage(String reason) {
        this.message.put("REASON", reason);
        return this;
    }

    public ApiResponse setMessage(String code, String reason) {
        this.message.put("CODE", code);
        this.message.put("REASON", reason);
        return this;
    }

    public ApiResponse setTrace(String traceMessage) {
        this.message.put("TRACE", traceMessage);
        return this;
    }

    public ApiResponse addResult(Map<String, Object> map) {
        this.result.putAll(map);
        return this;
    }

    public ApiResponse addResult(String key, Object value) {
        this.result.put(key, value);
        return this;
    }
}
