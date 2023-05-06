package com.black.utils.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author black
 * @date 2023/4/19 15:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException {

    private Integer code;  //异常状态码

    private String msg;  //异常消息

}
