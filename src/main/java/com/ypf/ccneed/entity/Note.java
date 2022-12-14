package com.ypf.ccneed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "Note对象", description = "")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String noteBody;

    private String noteTitle;

    private String noteDesc;

    private LocalDateTime uploadTime;

    private Integer userId;

    private String noteType;

}
