package com.example.board.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String pass) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setTo(to);
            helper.setSubject("임시 비밀번호 발급");

            String content = String.format("""
                    <div style='margin:100px;'>
                        <h1>임시 비밀번호 발급</h1>
                        <br>
                        <p>아래의 임시 비밀번호로 로그인해 주세요</p>
                        <br>
                        <p>임시 비밀번호 : <strong>%s</strong></p>
                        <br>
                        <p>로그인 후 반드시 비밀번호를 변경해 주세요.</p>
                        </div>
                    """, pass);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 발송에 실패했습니다.");
        }
    }
}
