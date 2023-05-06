package com.black.system.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author black
 * @date 2023/3/15 16:45
 */
@ApiModel("登录请求参数")
@Data
public class LoginReqVO {

    private String username;

    private String password;

}
