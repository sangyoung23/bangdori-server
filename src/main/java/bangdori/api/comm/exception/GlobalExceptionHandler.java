package bangdori.api.comm.exception;

import bangdori.api.comm.response.ApiResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static bangdori.api.comm.response.ApiResponse.VALUE_STATUS_INVALID;
import static bangdori.api.comm.response.ApiResponse.VALUE_STATUS_RUNTIME_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 모든 예외 공통 처리
    @ExceptionHandler(Exception.class)
    public ApiResponse handleAllExceptions(Exception e) {
        return new ApiResponse()
                .fail(VALUE_STATUS_RUNTIME_ERROR, "서버 처리 중 오류가 발생했습니다.")
                .setTrace(e.getMessage());
    }

    // 잘못된 요청 JSON 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse handleInvalidJson(HttpMessageNotReadableException e) {
        return new ApiResponse()
                .fail(VALUE_STATUS_INVALID, "요청 형식이 잘못되었습니다.")
                .setTrace(e.getMessage());
    }

    // @Valid 같은 파라미터 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationError(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ApiResponse()
                .fail(VALUE_STATUS_INVALID, "요청 데이터 오류: " + errorMessage)
                .setTrace(e.getMessage());
    }

    // 잘못된 인자를 전달 받았을 때
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse handleIllegalArgument(IllegalArgumentException e) {
        return new ApiResponse()
                .fail(VALUE_STATUS_INVALID, e.getMessage())
                .setTrace(e.getMessage());
    }

    // 파일 저장 실패 처리
    @ExceptionHandler(FileStorageException.class)
    public ApiResponse handleFileStorageException(FileStorageException e) {
        return new ApiResponse()
                .fail(VALUE_STATUS_RUNTIME_ERROR, "파일 저장 중 오류가 발생했습니다.")
                .setTrace(e.getMessage());
    }

}