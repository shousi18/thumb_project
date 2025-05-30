package com.shousi.thumb.controller;

import com.shousi.thumb.model.dto.thumb.DoThumbRequest;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.ThumbService;
import com.shousi.thumb.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thumb")
public class ThumbController {

    @Resource
    private ThumbService thumbService;

    @Resource
    private UserService userService;

    @PostMapping("/dothumb")
    public boolean doThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        // 参数校验
        if (doThumbRequest == null || doThumbRequest.getVideoId() == null || doThumbRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        // 获取当前登录用户
        UserVO loginUser = userService.getLoginUser(request);
        return thumbService.doThumb(doThumbRequest.getVideoId(), loginUser);
    }

    @PostMapping("/undothumb")
    public boolean undoThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        // 参数校验
        if (doThumbRequest == null || doThumbRequest.getVideoId() == null || doThumbRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        // 获取当前登录用户
        UserVO loginUser = userService.getLoginUser(request);
        return thumbService.undoThumb(doThumbRequest.getVideoId(), loginUser);
    }

    @PostMapping("/isThumb")
    public boolean isThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        if (doThumbRequest == null || doThumbRequest.getVideoId() == null || doThumbRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        UserVO loginUser = userService.getLoginUser(request);
        return thumbService.isThumb(doThumbRequest.getVideoId(), loginUser);
    }
}
