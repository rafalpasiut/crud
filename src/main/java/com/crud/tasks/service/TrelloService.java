package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.created.CreatedTrelloCard;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello card";
    private TrelloClient trelloClient;
    private SimpleEmailService emailService;
    private AdminConfig adminConfig;

    @Autowired
    public TrelloService(TrelloClient trelloClient, SimpleEmailService emailService, AdminConfig adminConfig) {
        this.trelloClient = trelloClient;
        this.emailService = emailService;
        this.adminConfig = adminConfig;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card ->
        emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "New card: " + trelloCardDto.getName() + " has been created on your Trello account", "")));

        return newCard;
    }
}
