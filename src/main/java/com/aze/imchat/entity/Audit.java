package com.aze.imchat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_audit")
@ApiModel(value = "Audit对象", description = "")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @ApiModelProperty("申请人id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("业务类型，联系人或者群 ")
    @TableField("business_type")
    private Integer businessType;

    @ApiModelProperty("业务id")
    @TableField("business_id")
    private Integer businessId;

    @ApiModelProperty("申请时间 ")
    @TableField("apply_time")
    private LocalDateTime applyTime;

    @ApiModelProperty("审核时间")
    @TableField("audit_time")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核用户的id")
    @TableField("audit_user_id")
    private Integer auditUserId;

    @ApiModelProperty("申请的理由")
    @TableField("apply_reason")
    private String applyReason;

    @ApiModelProperty("审核的理由")
    @TableField("audit_reason")
    private String auditReason;

    @ApiModelProperty("0 未审核 1 审核通过 2 审核拒绝")
    @TableField("status")
    private Integer status;


}




