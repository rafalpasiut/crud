package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.mail.MailType;
import com.crud.tasks.service.mail.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(cron = "0 * * * * *")
    public void sendInformationEmail() {
        List<Task> tasks = taskRepository.findAll();
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "", ""), MailType.DATABASE_TASK_DAILY_COUNT, obtainParameters(tasks));
    }

    private Map<String, Object> obtainParameters(List<Task> tasks) {
        Map<String, Object> params = new HashMap<>();
        params.put("tasks", tasks);
        params.put("count", tasks.size());
        return params;
    }
}
