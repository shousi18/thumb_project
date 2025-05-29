package com.shousi.thumb.service;

import com.shousi.thumb.model.dto.user.UserLoginRequest;
import com.shousi.thumb.model.dto.user.UserRegisterRequest;
import com.shousi.thumb.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shousi.thumb.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 86172
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-05-29 22:26:45
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    UserVO register(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    UserVO login(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    UserVO getLoginUser(HttpServletRequest request);

    /**
     * 实体类转vo，数据脱敏
     * @param user
     * @return
     */
    UserVO objToVO(User user);
}
