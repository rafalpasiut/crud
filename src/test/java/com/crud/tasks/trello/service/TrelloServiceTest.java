package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.created.AttachmentsByType;
import com.crud.tasks.domain.created.Badges;
import com.crud.tasks.domain.created.CreatedTrelloCardDto;
import com.crud.tasks.domain.created.Trello;
import com.crud.tasks.service.mail.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void createTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","desc","pos","listId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("id","name","url",new Badges(1,new AttachmentsByType(new Trello(1,1))));
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto cardDto = trelloService.createTrelloCard(trelloCardDto);
        //Then
        Assert.assertNotNull(cardDto);
        Assert.assertEquals(createdTrelloCardDto.getBadges().getVotes(),cardDto.getBadges().getVotes());
        Assert.assertEquals(createdTrelloCardDto.getId(),cardDto.getId());
        Assert.assertEquals(createdTrelloCardDto.getShortUrl(),cardDto.getShortUrl());
        Assert.assertEquals(createdTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getBoard(),cardDto.getBadges().getAttachmentsByType().getTrello().getBoard());
        Assert.assertEquals(createdTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getCard(),cardDto.getBadges().getAttachmentsByType().getTrello().getCard());
    }

    @Test
    public void createTrelloCardEmpty() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","desc","pos","listId");
        CreatedTrelloCardDto createdTrelloCardDto = null;
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto cardDto = trelloService.createTrelloCard(trelloCardDto);
        //Then
        Assert.assertNull(cardDto);
    }

    @Test
    public void fetchTrelloBoards(){
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("id1","name1",new ArrayList<>()));
        trelloBoardDtos.add(new TrelloBoardDto("id2","name2",new ArrayList<>()));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertEquals(2,trelloBoards.size());
        Assert.assertEquals("name2",trelloBoards.get(1).getName());
        Assert.assertEquals(0,trelloBoards.get(1).getLists().size());
    }
}