package com.shousi.thumb.model.dto.thumb;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoThumbRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    private Long videoId;
}
