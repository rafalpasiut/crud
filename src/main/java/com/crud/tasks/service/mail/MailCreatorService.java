package com.crud.tasks.service.mail;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    @Value("${frontend.uri}")
    private String frontEndUri;

    public String buildMail(String message, MailType mailType, Map<String, Object> params) {
        switch (mailType) {
            case TRELLO_TASK_CREATED:
                return buildTrelloCardEmail(message, params);
            case DATABASE_TASK_DAILY_COUNT:
                return buildDatabaseTaskDailyCountEmail(message, params);
            default:
                throw new IllegalStateException("Unrecognized mail type: " + mailType);
        }
    }

    public String buildTrelloCardEmail(String message, Map<String, Object> params) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        if (params != null) {
            fillContextWithData(context, params);
        }
        context.setVariable("message", message);
        context.setVariable("task_url", frontEndUri);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_config", companyConfig);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("email/created-trello-card-mail", context);
    }

    public String buildDatabaseTaskDailyCountEmail(String message, Map<String, Object> params) {

        Context context = new Context();
        if (params != null) {
            fillContextWithData(context, params);
        }
        context.setVariable("message", message);
        context.setVariable("task_url", frontEndUri);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_config", companyConfig);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        return templateEngine.process("email/database-tasks-daily-mail", context);
    }

    private void fillContextWithData(Context context, Map<String, Object> params) {
        for (Map.Entry<String, Object> param : params.entrySet()) {
            context.setVariable(param.getKey(), param.getValue());
        }
    }
}
