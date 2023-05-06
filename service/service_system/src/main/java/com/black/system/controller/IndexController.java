package com.black.system.controller;

import com.black.system.service.IndexService;
import com.black.system.vo.request.LoginReqVO;
import com.black.utils.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author black
 * @date 2023/4/27 10:01
 */
@RestController
@RequestMapping("/system/index")
@Api(tags = "索引信息接口")
public class IndexController {

    @Resource
    private IndexService indexService;

    @PostMapping("login")
    @ApiOperation("登录")
    public R<Map<String,String>> login(@RequestBody LoginReqVO reqVO){
        Map<String, String> map = indexService.login(reqVO);
        return R.ok(map);
    }

    @GetMapping ("info")
    @ApiOperation("根据token获取用户信息")
    public R info(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String,Object> userInfo = indexService.getUserInfo(request);
        return R.ok(userInfo);
    }

    @PostMapping("logout")
    @ApiOperation("退出登录")
    public R logout(){
        return R.ok();
    }

}
