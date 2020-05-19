package com.knowology.exception.handler;

import com.knowology.util.ResponseUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author : Conan
 * @Description Controller全局异常处理
 * @date : 2019-04-16 17:54
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Object bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = null;
        for (FieldError error:bindingResult.getFieldErrors()) {
            defaultMessage = error.getDefaultMessage();
        }
        return ResponseUtil.fail(defaultMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = null;
        for (FieldError error:bindingResult.getFieldErrors()) {
            defaultMessage = error.getDefaultMessage();
        }
        return ResponseUtil.fail(defaultMessage);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object methodFileSizeLimitExceededExceptionHandler(MaxUploadSizeExceededException e) {
        return ResponseUtil.fail("上传文件过大");
    }

}
