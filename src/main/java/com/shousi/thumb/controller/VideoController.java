package com.shousi.thumb.controller;

import com.shousi.thumb.model.dto.video.QueryVideoCountRequest;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.UserService;
import com.shousi.thumb.service.VideoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @Resource
    private UserService userService;

    @PostMapping("/upload")
    public Long uploadVideo(HttpServletRequest request) {
        UserVO loginUser = userService.getLoginUser(request);
        return videoService.uploadVideo(loginUser);
    }

    @PostMapping("/queryCount")
    public Long queryVideoCount(@RequestBody QueryVideoCountRequest queryVideoCountRequest) {
        if (queryVideoCountRequest == null || queryVideoCountRequest.getVideoId() == null || queryVideoCountRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        return videoService.queryVideoCount(queryVideoCountRequest.getVideoId());
    }
}
