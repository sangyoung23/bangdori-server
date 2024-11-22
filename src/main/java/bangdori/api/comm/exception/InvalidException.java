package bangdori.api.comm.exception;

import bangdori.api.comm.ErrorCode;

public class InvalidException extends ApiException {

    private static final long serialVersionUID = 1L;

    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidException(ErrorCode errorCode, String str1) {
        super(errorCode, str1);
    }

    public InvalidException(ErrorCode errorCode, String str1, String str2) {
        super(errorCode, str1, str2);
    }

    public InvalidException(ErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    public InvalidException(ErrorCode errorCode, String str1, Throwable e) {
        super(errorCode, str1, e);
    }

    public InvalidException(ErrorCode errorCode, String str1, String str2, Throwable e) {
        super(errorCode, str1, str2, e);
    }
}

//사용예제 - 예외 처리시 invalid 예외 생성
//throw new InvalidException(ErrorCode.CMI1001);