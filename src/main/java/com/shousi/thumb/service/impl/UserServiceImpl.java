package com.shousi.thumb.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.mapper.UserMapper;
import com.shousi.thumb.model.dto.user.UserLoginRequest;
import com.shousi.thumb.model.dto.user.UserRegisterRequest;
import com.shousi.thumb.model.entity.User;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author 86172
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-05-29 22:26:45
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "shousi";

    @Resource
    private UserMapper userMapper;

    @Override
    public UserVO register(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String confirmPassword = userRegisterRequest.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("两次密码不一致");
        }
        // 判断账号是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        if (userMapper.selectOne(lambdaQueryWrapper) != null) {
            throw new RuntimeException("用户已存在");
        }
        // 插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword(password));
        long timeMillis = System.currentTimeMillis();
        user.setUsername("用户" + timeMillis);
        userMapper.insert(user);
        return objToVO(user);
    }

    @Override
    public UserVO login(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        // 对比加密后的密码
        String encryptPassword = encryptPassword(userPassword);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        // 用户不存在
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 存在则存到session中
        request.getSession().setAttribute("user", user);
        return objToVO(user);
    }

    @Override
    public UserVO getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new RuntimeException("未登录");
        }
        return objToVO(user);
    }

    @Override
    public UserVO objToVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    private String encryptPassword(String password) {
        // 加密逻辑
        return DigestUtil.md5Hex(password + SALT);
    }
}




