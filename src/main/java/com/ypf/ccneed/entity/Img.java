package com.ypf.ccneed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "Img对象", description = "")
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String imgSrc;

    private String imgDesc;

    private String imgText;

    @ApiModelProperty("0 默认，1 壁纸，2 日常，3 卡通，4 其他")
    private Integer imgType;

    private LocalDateTime uploadTime;
    private Integer userId=3;


}
