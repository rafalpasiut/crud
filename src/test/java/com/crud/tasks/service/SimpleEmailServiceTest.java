package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.mail.MailType;
import com.crud.tasks.service.mail.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    SimpleEmailService simpleEmailService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void shoudSendEmail() {
        //Given
        Mail mail = new Mail("test@gmail.com", "test", "test", "testcc@gmail.com");

        //When
        simpleEmailService.send(mail, MailType.DATABASE_TASK_DAILY_COUNT, null);

        //Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void shoudSendEmailEmptyCc() {
        //Given
        Mail mail = new Mail("test@gmail.com", "test", "test", "");

        //When
        simpleEmailService.send(mail, MailType.DATABASE_TASK_DAILY_COUNT, null);

        //Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

}