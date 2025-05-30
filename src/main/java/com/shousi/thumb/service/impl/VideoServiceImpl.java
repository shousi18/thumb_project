package com.shousi.thumb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.mapper.VideoMapper;
import com.shousi.thumb.model.dto.video.QueryVideoCountRequest;
import com.shousi.thumb.model.entity.Video;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.VideoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 86172
 * @description 针对表【video(视频表)】的数据库操作Service实现
 * @createDate 2025-05-29 22:26:45
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
        implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public Long uploadVideo(UserVO loginUser) {
        Video video = new Video();
        video.setCreateId(loginUser.getId());
        int insert = videoMapper.insert(video);
        if (insert != 1) {
            throw new RuntimeException("上传视频失败");
        }
        return video.getId();
    }

    @Override
    public Long queryVideoCount(QueryVideoCountRequest queryVideoCountRequest) {
        Long videoId = queryVideoCountRequest.getVideoId();
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getId, videoId);
        Video video = videoMapper.selectOne(queryWrapper);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        // 返回视频点赞数
        return video.getThumbCount();
    }
}




