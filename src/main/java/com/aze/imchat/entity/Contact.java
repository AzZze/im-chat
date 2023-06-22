package com.aze.imchat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author aze
 * @since 2023-06-22
 */
@Data
@TableName("t_contact")
@ApiModel(value = "Contact对象", description = "")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @ApiModelProperty("用户id ")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("好友id")
    @TableField("friend_id")
    private Integer friendId;

    @ApiModelProperty("申请时间")
    @TableField("apply_time")
    private LocalDateTime applyTime;

    @ApiModelProperty("审核时间 ")
    @TableField("audit_time")
    private LocalDateTime auditTime;
}




