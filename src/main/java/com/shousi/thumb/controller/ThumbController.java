package com.shousi.thumb.controller;

import com.shousi.thumb.model.dto.thumb.DoThumbRequest;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.model.vo.VideoThumbVO;
import com.shousi.thumb.service.ThumbService;
import com.shousi.thumb.service.UserService;
import com.shousi.thumb.service.VideoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/thumb")
public class ThumbController {

    @Resource
    private ThumbService thumbService;

    @Resource
    private UserService userService;

    @Resource
    private VideoService videoService;

    @PostMapping("/dothumb")
    public boolean doThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        // 参数校验
        if (Objects.isNull(doThumbRequest) || doThumbRequest.getVideoId() == null || doThumbRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        // 获取当前登录用户
        UserVO loginUser = userService.getLoginUser(request);
        return thumbService.doThumb(doThumbRequest.getVideoId(), loginUser);
    }

    @PostMapping("/undothumb")
    public boolean undoThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        // 参数校验
        if (Objects.isNull(doThumbRequest) || doThumbRequest.getVideoId() == null || doThumbRequest.getVideoId() <= 0) {
            throw new RuntimeException("参数错误");
        }
        // 获取当前登录用户
        UserVO loginUser = userService.getLoginUser(request);
        return thumbService.undoThumb(doThumbRequest.getVideoId(), loginUser);
    }

    @PostMapping("/isThumb")
    public VideoThumbVO isThumb(@RequestBody DoThumbRequest doThumbRequest, HttpServletRequest request) {
        Long videoId = doThumbRequest.getVideoId();
        if (Objects.isNull(doThumbRequest) || videoId == null || videoId <= 0) {
            throw new RuntimeException("参数错误");
        }
        UserVO loginUser = userService.getLoginUser(request);
        // 判断是否点赞
        boolean thumb = thumbService.isThumb(videoId, loginUser);
        // 获取该视频的点赞数量
        Long thumbCount = videoService.queryVideoCount(videoId);
        // 封装返回数据
        VideoThumbVO videoThumbVO = new VideoThumbVO();
        videoThumbVO.setVideoId(videoId);
        videoThumbVO.setIsThumb(thumb);
        videoThumbVO.setThumbCount(thumbCount);
        return videoThumbVO;
    }
}
