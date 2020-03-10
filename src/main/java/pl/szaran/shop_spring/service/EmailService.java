package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void send(String to, String subject, String message) {

        if (to == null) {
            throw new MyException(ExceptionCode.SERVICE, "EMAIL SERVICE: send() method, to is null");
        }
        if (subject == null) {
            throw new MyException(ExceptionCode.SERVICE, "EMAIL SERVICE: send() method, subject is null");
        }
        if (message == null) {
            throw new MyException(ExceptionCode.SERVICE, "EMAIL SERVICE: send() method, message is null");
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
}
