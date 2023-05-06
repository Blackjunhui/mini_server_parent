package com.black.utils.exceptionHandler;

import com.black.utils.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author black
 * @date 2023/4/19 15:57
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public static R error(Exception e){
//        e.printStackTrace();
//        return R.fail("调用了全局异常处理..");
//    }

}
