package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.created.AttachmentsByType;
import com.crud.tasks.domain.created.Badges;
import com.crud.tasks.domain.created.CreatedTrelloCard;
import com.crud.tasks.domain.created.Trello;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("testUser");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/testUser/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTellBoards = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(1, fetchedTellBoards.size());
        Assert.assertEquals("test_id", fetchedTellBoards.get(0).getId());
        Assert.assertEquals("test_board", fetchedTellBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchedTellBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateTask() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test description", "top", "test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard("1", "Test task", "http://test.com", new Badges(1, new AttachmentsByType(new Trello(1, 1))));
        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        Assert.assertEquals(createdTrelloCard.getId(), newCard.getId());
        Assert.assertEquals(createdTrelloCard.getName(), newCard.getName());
        Assert.assertEquals(createdTrelloCard.getShortUrl(), newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {

        //Given
        URI uri = new URI("http://test.com/members/testUser/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> requestBoards = trelloClient.getTrelloBoards();

        //Then
        Assert.assertNotNull(requestBoards);
        Assert.assertEquals(0, requestBoards.size());
    }
}