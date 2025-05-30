package com.shousi.thumb.service;

import com.shousi.thumb.model.entity.Thumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shousi.thumb.model.vo.UserVO;

/**
* @author 86172
* @description 针对表【thumb(点赞表)】的数据库操作Service
* @createDate 2025-05-29 22:26:45
*/
public interface ThumbService extends IService<Thumb> {

    /**
     * 点赞
     *
     * @param videoId
     * @param loginUser
     * @return
     */
    boolean doThumb(Long videoId, UserVO loginUser);

    /**
     * 取消点赞
     * @param videoId
     * @param loginUser
     * @return
     */
    boolean undoThumb(Long videoId, UserVO loginUser);

    /**
     * 判断当前用户是否点赞
     *
     * @param videoId
     * @param request
     * @return
     */
    boolean isThumb(Long videoId, UserVO request);
}
