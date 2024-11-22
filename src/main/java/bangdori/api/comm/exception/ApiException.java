package bangdori.api.comm.exception;

import bangdori.api.comm.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;
    private String str1 = null;
    private String str2 = null;

    public ApiException(ErrorCode errorCode) {
        this.set(errorCode);
    }

    public ApiException(ErrorCode errorCode, String str1) {
        this.set(errorCode, str1);
    }

    public ApiException(ErrorCode errorCode, String str1, String str2) {
        this.set(errorCode, str1, str2);
    }

    public ApiException(ErrorCode errorCode, Throwable e) {
        super(e);
        this.set(errorCode);
    }

    public ApiException(ErrorCode errorCode, String str1, Throwable e) {
        super(e);
        this.set(errorCode, str1);
    }

    public ApiException(ErrorCode errorCode, String str1, String str2, Throwable e) {
        super(e);
        this.set(errorCode, str1, str2);
    }

    private void set(ErrorCode errorCode, String str1, String str2) {
        this.errorCode = errorCode;
        this.str1 = str1;
        this.str2 = str2;
    };

    private void set(ErrorCode errorCode) {
        this.set(errorCode, null, null);
    };

    private void set(ErrorCode errorCode, String str1) {
        this.set(errorCode, str1, null);
    };
}