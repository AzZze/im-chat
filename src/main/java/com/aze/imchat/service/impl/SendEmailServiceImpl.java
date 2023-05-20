package com.aze.imchat.service.impl;

import com.aze.imchat.entity.MailRequest;
import com.aze.imchat.exceptions.ServiceException;
import com.aze.imchat.service.SendEmailService;
import com.aze.imchat.utils.StringUtils;
import com.aze.imchat.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {

    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private  final  static String USER_EMAIL_CODE_PREFIX="users:email:code:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final static  Integer TIME_INTERVAL=60*1000*10;


    @Override
    public void sendEmail(String email) {

        String key=USER_EMAIL_CODE_PREFIX.concat(email);
        String value  = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            throw  new ServiceException("请等待一段时间后再发送验证码");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(email);
        //邮件主题
        message.setSubject("注册验证码");
        String code = IdUtils.fastSimpleUUID().substring(0, 6);

        stringRedisTemplate.opsForValue().set(key,code,600, TimeUnit.SECONDS);
        //邮件内容
        message.setText(code);
        //邮件发送时间
        message.setSentDate(new Date());

        javaMailSender.send(message);
        log.info("发送邮件成功:{}->{}",sendMailer,email);

    }
}
