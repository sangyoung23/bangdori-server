package bangdori.api.comm.exception;

import bangdori.api.comm.ErrorCode;

public class ErrorException extends ApiException {

    private static final long serialVersionUID = 1L;

    public ErrorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ErrorException(ErrorCode errorCode, String str1) {
        super(errorCode, str1);
    }

    public ErrorException(ErrorCode errorCode, String str1, String str2) {
        super(errorCode, str1, str2);
    }

    public ErrorException(ErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    public ErrorException(ErrorCode errorCode, String str1, Throwable e) {
        super(errorCode, str1, e);
    }

    public ErrorException(ErrorCode errorCode, String str1, String str2, Throwable e) {
        super(errorCode, str1, str2, e);
    }
}
