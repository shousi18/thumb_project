package com.shousi.thumb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.constant.RedisKeyConstant;
import com.shousi.thumb.mapper.VideoMapper;
import com.shousi.thumb.model.entity.Video;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.model.vo.VideoVO;
import com.shousi.thumb.service.VideoService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
    public Long queryVideoCount(Long videoId) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getId, videoId);
        Video video = videoMapper.selectOne(queryWrapper);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        // 返回视频点赞数
        return video.getThumbCount();
    }

    @Override
    public List<VideoVO> getTop(int size) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisKeyConstant.VIDEO_TOP_KEY, 0, size - 1L);
        List<VideoVO> videoVOList = Objects.requireNonNull(typedTuples)
                .stream()
                .map(obj -> {
                    VideoVO videoVO = new VideoVO();
                    videoVO.setId(Long.valueOf(obj.getValue().toString()));
                    videoVO.setThumbCount(obj.getScore().longValue());
                    return videoVO;
                })
                .toList();
        // 如果不为空直接返回
        if (!videoVOList.isEmpty()) {
            return videoVOList;
        }
        // 为空尝试查询数据库
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Video::getId, Video::getThumbCount);
        queryWrapper.orderByDesc(Video::getThumbCount);
        queryWrapper.last("limit " + size);
        videoVOList = videoMapper.selectList(queryWrapper)
                .stream()
                .map(video -> {
                    VideoVO videoVO = new VideoVO();
                    videoVO.setId(video.getId());
                    videoVO.setThumbCount(video.getThumbCount());
                    return videoVO;
                })
                .toList();
        if (!videoVOList.isEmpty()) {
            // 缓存到redis
            for (VideoVO videoVO : videoVOList) {
                redisTemplate.opsForZSet().add(RedisKeyConstant.VIDEO_TOP_KEY, videoVO.getId(), videoVO.getThumbCount());
            }
        }
        return videoVOList;
    }
}




