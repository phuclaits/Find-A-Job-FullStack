package com.doan.AppTuyenDung.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.doan.AppTuyenDung.DTO.Response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException) {
    	ApiResponse apiRespones = new ApiResponse();
        apiRespones.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiRespones.setMessage(runtimeException.getMessage());
        return ResponseEntity.badRequest().body(apiRespones);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse apiRespones = new ApiResponse();
        apiRespones.setCode(errorCode.getCode());
        apiRespones.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespones);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingVadidation(MethodArgumentNotValidException ex) {
        String enumKey = ex.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalAccessError i) {

        }

        ApiResponse apiRespones = new ApiResponse();
        apiRespones.setCode(errorCode.getCode());
        apiRespones.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespones);
    }
}
