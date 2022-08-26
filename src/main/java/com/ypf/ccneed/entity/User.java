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
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String nickname;

    @ApiModelProperty("0 是，其他不是")
    private Integer admin;

    private String phone;

    private String email;

    private String salt;

    private String openId;

    @ApiModelProperty("0 下线 1 上线")
    private Integer userStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
