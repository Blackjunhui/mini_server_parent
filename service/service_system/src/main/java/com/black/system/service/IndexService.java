package com.black.system.service;

import com.black.system.entity.SysUser;
import com.black.system.vo.request.LoginReqVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author black
 * @date 2023/4/27 10:07
 */
public interface IndexService {

    Map<String,Object> getUserInfo(HttpServletRequest request);

    Map<String, String> login(LoginReqVO reqVO);
}
