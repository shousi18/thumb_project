package com.shousi.thumb.utils;

import com.shousi.thumb.constant.RedisKeyConstant;

/**
 * 构造 redis key
 */
public class RedisKeyUtil {

    public static String getUserThumbKey(Long userId) {
        return RedisKeyConstant.USER_THUMB_KEY + userId;
    }
}
