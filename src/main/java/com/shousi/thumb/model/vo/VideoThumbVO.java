package com.shousi.thumb.model.vo;

import lombok.Data;

@Data
public class VideoThumbVO {
    /**
     *  视频id
     */
    private Long videoId;

    /**
     * 点赞数量
     */
    private Long thumbCount;

    /**
     * 是否点赞
     */
    private Boolean isThumb;
}
