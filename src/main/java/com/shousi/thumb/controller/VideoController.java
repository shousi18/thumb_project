package com.shousi.thumb.controller;

import com.shousi.thumb.model.dto.video.QueryVideoCountRequest;
import com.shousi.thumb.model.entity.Video;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.model.vo.VideoVO;
import com.shousi.thumb.service.UserService;
import com.shousi.thumb.service.VideoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/getTop")
    public List<VideoVO> getTop() {
        int size = 10;
        return videoService.getTop(size);
    }
}
