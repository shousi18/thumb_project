package com.shousi.thumb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.mapper.ThumbMapper;
import com.shousi.thumb.mapper.VideoMapper;
import com.shousi.thumb.model.entity.Thumb;
import com.shousi.thumb.model.entity.Video;
import com.shousi.thumb.model.vo.UserVO;
import com.shousi.thumb.service.ThumbService;
import com.shousi.thumb.utils.RedisKeyUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 86172
 * @description 针对表【thumb(点赞表)】的数据库操作Service实现
 * @createDate 2025-05-29 22:26:45
 */
@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
        implements ThumbService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private ThumbMapper thumbMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean doThumb(Long videoId, UserVO loginUser) {
        // 判断视频是否存在
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        Long userId = loginUser.getId();
        if (userId == null || userId <= 0) {
            throw new RuntimeException("用户不存在");
        }
        // 判断是否点过赞
        if (isThumb(videoId, loginUser)) {
            throw new RuntimeException("不能重复点赞");
        }
        // 进行点赞
        Thumb thumb = new Thumb();
        thumb.setUserId(userId);
        thumb.setVideoId(videoId);
        int insert = thumbMapper.insert(thumb);
        if (insert != 1) {
            throw new RuntimeException("网络问题，点赞失败");
        }
        // 更新视频点赞数
        LambdaUpdateWrapper<Video> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Video::getId, videoId);
        wrapper.setSql("thumb_count = thumb_count + 1");
        int update = videoMapper.update(wrapper);
        if (update != 1) {
            // todo 可以不进行报错，点赞数据可以后期补偿
            throw new RuntimeException("网络问题，点赞失败");
        }
        // 存入Redis进行缓存
        redisTemplate.opsForHash().put(RedisKeyUtil.getUserThumbKey(userId), videoId.toString(), true);
        return true;
    }

    @Override
    public boolean undoThumb(Long videoId, UserVO loginUser) {
        // 判断视频是否存在
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        Long userId = loginUser.getId();
        if (userId == null || userId <= 0) {
            throw new RuntimeException("用户不存在");
        }
        // 判断用户是否点过赞
        LambdaQueryWrapper<Thumb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Thumb::getVideoId, videoId);
        queryWrapper.eq(Thumb::getUserId, userId);
        Thumb thumb = thumbMapper.selectOne(queryWrapper);
        if (thumb == null) {
            throw new RuntimeException("用户未点赞");
        }
        // 取消点赞
        int delete = thumbMapper.delete(queryWrapper);
        if (delete != 1) {
            throw new RuntimeException("网络问题，取消点赞失败");
        }
        // 更新视频点赞数
        LambdaUpdateWrapper<Video> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Video::getId, videoId);
        wrapper.setSql("thumb_count = thumb_count - 1");
        int update = videoMapper.update(wrapper);
        if (update != 1) {
            throw new RuntimeException("网络问题，取消点赞失败");
        }
        // 删除Redis缓存
        redisTemplate.opsForHash().delete(RedisKeyUtil.getUserThumbKey(userId), videoId.toString());
        return true;
    }

    @Override
    public boolean isThumb(Long videoId, UserVO loginUser) {
        // 判断视频是否存在
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        Long userId = loginUser.getId();
        if (userId == null || userId <= 0) {
            throw new RuntimeException("用户不存在");
        }
        // 可以先查缓存，再查数据库
        String userThumbKey = RedisKeyUtil.getUserThumbKey(userId);
        // 查询该用户是否点赞了该视频
        Object obj = redisTemplate.opsForHash().get(userThumbKey, videoId.toString());
        if (obj != null) {
            return true;
        }
        // 查询数据库
        LambdaQueryWrapper<Thumb> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Thumb::getVideoId, videoId);
        queryWrapper.eq(Thumb::getUserId, userId);
        Thumb thumb = thumbMapper.selectOne(queryWrapper);
        if (thumb == null) {
            // 用户未点赞
            return false;
        }
        // 用户点过赞，存入到redis中
        redisTemplate.opsForHash().put(userThumbKey, videoId.toString(), true);
        return true;
    }
}




