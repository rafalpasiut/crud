package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    private SimpleEmailService simpleEmailService;
    private TaskRepository taskRepository;
    private AdminConfig adminConfig;

    @Autowired
    public EmailScheduler(SimpleEmailService simpleEmailService, TaskRepository taskRepository, AdminConfig adminConfig) {
        this.simpleEmailService = simpleEmailService;
        this.taskRepository = taskRepository;
        this.adminConfig = adminConfig;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long count = taskRepository.count();
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, createEmailMessage(count), ""));
    }

    private String createEmailMessage(long count) {
        if (count == 0) {
            return "Currently you don`t have any tasks in your database.";
        } else if (count == 1) {
            return "Currently in database you`ve got: 1 task.";
        } else {
            return "Currently in database you`ve got: " + count + " tasks.";
        }
    }
}
