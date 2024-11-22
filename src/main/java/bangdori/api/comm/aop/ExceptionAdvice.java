package bangdori.api.comm.aop;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import bangdori.api.comm.ApiResponse;
import bangdori.api.comm.exception.ApiException;
import bangdori.api.comm.exception.ErrorException;
import bangdori.api.comm.exception.InvalidException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdvice {

    @Value("${server.error.include-trace-on-error}")
    private boolean includeTrace;

    // InvalidException - custom error
    @ExceptionHandler(InvalidException.class)
    public ApiResponse handleInvalidException(InvalidException e, HttpServletRequest request) {
        ApiResponse res = new ApiResponse().invalid().setMessage(e.getErrorCode(), e.getStr1(), e.getStr2()).setTrace(e);
        res.setErrorInfoInRequest(request);
        if (!includeTrace)
            res.clearTrace();
        return res;
    }

    // ErrorException - custom error
    @ExceptionHandler(ErrorException.class)
    public ApiResponse handleErrorException(ApiException e, HttpServletRequest request) {
        ApiResponse res = new ApiResponse().error().setMessage(e.getErrorCode(), e.getStr1(), e.getStr2()).setTrace(e);
        res.setErrorInfoInRequest(request);
        if (!includeTrace)
            res.clearTrace();
        return res;
    }

    // NoHandlerFoundException
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        ApiResponse res = new ApiResponse().notFound();
        res.setErrorInfoInRequest(request);
        return res;
    }

    // HttpRequestMethodNotSupportedException
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                    HttpServletRequest request) {
        ApiResponse res = new ApiResponse().notSupportMethod();
        res.setErrorInfoInRequest(request);
        return res;
    }

    // HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                             HttpServletRequest request) {
        ApiResponse res = new ApiResponse().badRequest();
        res.setErrorInfoInRequest(request);
        return res;
    }


    // SQLSyntaxErrorException
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ApiResponse handleSQLSyntaxErrorException(SQLSyntaxErrorException e, HttpServletRequest request) {
        e.printStackTrace();
        ApiResponse res = new ApiResponse().sqlError().setTrace(e);
        res.setErrorInfoInRequest(request);
        if (!includeTrace)
            res.clearTrace();
        return res;
    }

    // SQLException
    @ExceptionHandler(SQLException.class)
    public ApiResponse handleSQLException(SQLException e, HttpServletRequest request) {
        e.printStackTrace();
        ApiResponse res = new ApiResponse().sqlError().setTrace(e);
        res.setErrorInfoInRequest(request);
        if (!includeTrace)
            res.clearTrace();
        return res;
    }
}