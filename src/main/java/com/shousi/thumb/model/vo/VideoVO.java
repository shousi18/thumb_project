package com.shousi.thumb.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    private Long id;

    /**
     * 点赞数
     */
    private Long thumbCount;
}
