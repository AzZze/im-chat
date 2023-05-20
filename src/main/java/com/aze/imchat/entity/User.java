package com.aze.imchat.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * user
 * </p>
 *
 * @author aze
 * @since 2023-05-20
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象", description = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("E-MAIL")
    @TableField("email")
    private String email;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("性别")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("头象")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("签名")
    @TableField("personal_signature")
    private String personalSignature;

    @ApiModelProperty("生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("最近登录时间")
    @TableField("last_login_time")
    private Long lastLoginTime;

    @ApiModelProperty("是否激活")
    @TableField("activate")
    private Boolean activate;

    @ApiModelProperty("激活时间")
    @TableField("activate_time")
    private Long activateTime;

    @ApiModelProperty("渠道来源")
    @TableField("channel")
    private String channel;

    @ApiModelProperty("SECRET MOBILE")
    @TableField("secret_mobile")
    private String secretMobile;

    @ApiModelProperty("设备")
    @TableField("device")
    private String device;

    @ApiModelProperty("设备id")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty("设备模型")
    @TableField("device_model")
    private String deviceModel;

    @ApiModelProperty("ip")
    @TableField("ip")
    private Long ip;

    @ApiModelProperty("STATUS")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")

    @TableField(fill= FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(value = "gmt_modified",fill =FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}




