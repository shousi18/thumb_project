package com.shousi.thumb.model.dto.video;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryVideoCountRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    private Long videoId;
}
