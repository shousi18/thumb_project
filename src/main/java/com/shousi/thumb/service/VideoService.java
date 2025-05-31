package com.shousi.thumb.service;

import com.shousi.thumb.model.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shousi.thumb.model.vo.UserVO;

/**
* @author 86172
* @description 针对表【video(视频表)】的数据库操作Service
* @createDate 2025-05-29 22:26:45
*/
public interface VideoService extends IService<Video> {

    /**
     * 上传视频
     * @return
     */
    Long uploadVideo(UserVO request);

    /**
     * 查询视频点赞数量
     * @param videoId
     * @return
     */
    Long queryVideoCount(Long videoId);
}
