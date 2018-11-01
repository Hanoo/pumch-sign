package cn.pumch.web.service.impl;

import cn.pumch.web.service.MailService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;



public class MailServiceImpl implements MailService {

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String host;
    private String from;
    private String displayName;
    private String pwd;
    /**
     * 发送邮件
     * @param toEmail 接收人地址，仅限一个
     * @param subject 标题
     * @param content 内容正文
     * @throws MessagingException
     */
    public void sendMail(String toEmail, String subject, String content)
            throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {

        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        // 发送邮箱的邮件服务器
        senderImpl.setHost(host);

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        // 为防止乱码，添加编码集设置
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");

        // 接收方邮箱
        messageHelper.setTo(toEmail);
        // 发送方邮箱
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        messageHelper.setFrom(from, displayName);

        Properties prop = new Properties();
        // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.auth", "true");
        // 超时时间
        prop.put("mail.smtp.timeout", "25000");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //新增配置 20180525
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.socketFactory", sf);
        prop.put("mail.smtp.socketFactory.port","587");
        prop.put("mail.smtp.socketFactory.fallback", "true");
        // 添加验证
        Authenticator auth = new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pwd);
            }
        };

        Session session = Session.getDefaultInstance(prop, auth);
        senderImpl.setSession(session);

        // 发送邮件
        senderImpl.send(mailMessage);
        logger.info("向"+toEmail+"发送密码找回邮件。");

    }

    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
}

