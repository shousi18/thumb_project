package com.shousi.thumb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.model.entity.User;
import com.shousi.thumb.service.UserService;
import com.shousi.thumb.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86172
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-05-29 22:26:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




