package com.black.security.exceptionHandler;

import com.black.utils.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author black
 * @date 2023/5/6 14:54
 */
@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e){
        e.printStackTrace();
        return R.fail("用户没有访问权限，请联系管理员!");
    }

}
