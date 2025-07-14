package org.sid.gestion_v.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void sendHtmlEmail(String to, String subject, String htmlBody);
}
