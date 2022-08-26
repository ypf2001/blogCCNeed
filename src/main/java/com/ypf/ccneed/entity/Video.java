package com.ypf.ccneed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ypf
 * @since 2022-07-27
 */
@Getter
@Setter
@ApiModel(value = "Video对象", description = "")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String videoSrc;

    private LocalDateTime uploadTime;

    private Integer userId;

    private String videoText;


}
