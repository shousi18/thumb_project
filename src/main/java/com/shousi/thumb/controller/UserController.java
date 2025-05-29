package com.shousi.thumb.controller;

import com.shousi.thumb.model.dto.user.UserLoginRequest;
import com.shousi.thumb.model.dto.user.UserRegisterRequest;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public UserVO register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null || userRegisterRequest.getUserAccount() == null || userRegisterRequest.getPassword() == null || userRegisterRequest.getConfirmPassword() == null) {
            throw new RuntimeException("参数错误");
        }
        return userService.register(userRegisterRequest);
    }


    @PostMapping("/login")
    public UserVO login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || userLoginRequest.getUserAccount() == null || userLoginRequest.getUserAccount().length() <= 0 || userLoginRequest.getUserPassword() == null || userLoginRequest.getUserPassword().length() <= 0) {
            throw new RuntimeException("参数错误");
        }
        return userService.login(userLoginRequest, request);
    }

    @GetMapping("/getLoginUser")
    public UserVO getLoginUser(HttpServletRequest request) {
        if (request == null) {
            throw new RuntimeException("参数错误");
        }
        return userService.getLoginUser(request);
    }
}
