package com.shousi.thumb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shousi.thumb.model.entity.Video;
import com.shousi.thumb.service.VideoService;
import com.shousi.thumb.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author 86172
* @description 针对表【video(视频表)】的数据库操作Service实现
* @createDate 2025-05-29 22:26:45
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService {

}




