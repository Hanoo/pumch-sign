package cn.pumch.web.service;

public interface MailService {
    void sendMail(String toEmail, String subject, String content) throws Exception;
}
