package bangdori.api.comm;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApiResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_MESSAGE = "MESSAGE";
    public static final String KEY_MESSAGE_CODE = "CODE";
    public static final String KEY_MESSAGE_REASON = "REASON";
    public static final String KEY_MESSAGE_TRACE = "TRACE";
    public static final String KEY_RESULT = "RESULT";

    public static final String VALUE_STATUS_SUCCESS = "200";
    public static final String VALUE_STATUS_INVALID = "400";
    public static final String VALUE_STATUS_AUTH_TOKEN_ERROR = "401";
    public static final String VALUE_STATUS_AUTH_ = "403";
    public static final String VALUE_STATUS_NOT_FOUND = "404";
    public static final String VALUE_STATUS_NOT_SUPPORT_METHOD = "404";
    public static final String VALUE_STATUS_RUNTIME_ERROR = "500";

    private Map<String, Object> result = new HashMap<String, Object>();
    private Map<String, Object> message = new HashMap<String, Object>();

    public ApiResponse() {
        this.put(ApiResponse.KEY_RESULT, this.result);
        this.put(ApiResponse.KEY_MESSAGE, this.message);
        this.success();
    }

    public ApiResponse success() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_SUCCESS);
        this.setMessage();
        return this;
    }

    public ApiResponse error() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_RUNTIME_ERROR);
        this.setMessage(ErrorCode.CME0001);
        return this;
    }

    public ApiResponse invalid() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_INVALID);
        this.setMessage(ErrorCode.CME0001);
        return this;
    }

    public ApiResponse notFound() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_NOT_FOUND);
        this.setMessage(ErrorCode.CME9001);
        return this;
    }

    public ApiResponse notSupportMethod() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_NOT_SUPPORT_METHOD);
        this.setMessage(ErrorCode.CME9002);
        return this;
    }

    public ApiResponse badRequest() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_INVALID);
        this.setMessage(ErrorCode.CME9003);
        return this;
    }

    public ApiResponse authTokenError() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_AUTH_TOKEN_ERROR);
        return this;
    }

    public ApiResponse sqlError() {
        this.put(ApiResponse.KEY_STATUS, ApiResponse.VALUE_STATUS_RUNTIME_ERROR);
        this.setMessage(ErrorCode.CME0002);
        return this;
    }

    private ApiResponse setMessage(String code, String reason) {
        this.message.put(ApiResponse.KEY_MESSAGE_CODE, code);
        this.message.put(ApiResponse.KEY_MESSAGE_REASON, reason);
        return this;
    }

    private ApiResponse setMessage() {
        String empty = null;
        return setMessage(empty, empty);
    }

    public ApiResponse setMessage(ErrorCode errorCode) {
        return setMessage(errorCode, null, null);
    }

    public ApiResponse setMessage(ErrorCode errorCode, String str1) {
        return setMessage(errorCode, str1, null);
    }

    public ApiResponse setMessage(ErrorCode errorCode, String str1, String str2) {
        return setMessage(errorCode.getCode(), String.format(errorCode.getErrorMessage(), str1, str2));
    }

    public ApiResponse setTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.message.put(ApiResponse.KEY_MESSAGE_TRACE, sw.toString());
        return this;
    }

    public ApiResponse clearTrace() {
        this.message.remove(ApiResponse.KEY_MESSAGE_TRACE);
        return this;
    }

    public ApiResponse addResult(String key, Object val) {
        this.result.put(key, val);
        return this;
    }

    public ApiResponse addResult(List<Map<String, Object>> list) {
        return addResult(Constants.KEY_LIST, list);
    }

    public ApiResponse addResult(Map<String, Object> map) {
        this.result.putAll(map);
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setErrorInfoInRequest(HttpServletRequest request) {
        request.setAttribute(KEY_STATUS, this.get(KEY_STATUS));
        request.setAttribute(KEY_MESSAGE_CODE, message.get(KEY_MESSAGE_CODE));
        request.setAttribute(KEY_MESSAGE_REASON, message.get(KEY_MESSAGE_REASON));
        request.setAttribute(KEY_MESSAGE_TRACE, message.get(KEY_MESSAGE_TRACE));
    }
}
