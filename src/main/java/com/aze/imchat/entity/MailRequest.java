package com.aze.imchat.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailRequest implements Serializable {

    /**
     * 接收人
     */
    private String sendTo;

    /**
     *  邮件主题
     */
    private String subject;

    /**
     *  邮件内容
     */
    private String text;



}
