package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {

    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void mapToBoardsNull() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = null;
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        Assert.assertEquals(0, trelloBoards.size());
        Assert.assertNotNull(trelloBoards);
    }

    @Test
    public void mapToBoardsEmptyBoard() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        Assert.assertEquals(0, trelloBoards.size());
        Assert.assertNotNull(trelloBoards);
    }

    @Test
    public void mapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "testName", new ArrayList<TrelloListDto>()));
        trelloBoardDtos.add(new TrelloBoardDto("2", "testName2", new ArrayList<TrelloListDto>()));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        Assert.assertEquals(2, trelloBoards.size());
    }

    @Test
    public void mapToBoardsDtoNull() {
        //Given
        List<TrelloBoard> trelloBoards = null;
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        Assert.assertEquals(0, trelloBoardDtos.size());
        Assert.assertNotNull(trelloBoardDtos);
    }

    @Test
    public void mapToBoardsDtoEmptyList() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        Assert.assertEquals(0, trelloBoardDtos.size());
        Assert.assertNotNull(trelloBoardDtos);
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("test1", "test1", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("test2", "test2", new ArrayList<>()));
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        Assert.assertEquals(2, trelloBoardDtos.size());
    }

    @Test
    public void mapToListNull() {
        //Given
        List<TrelloListDto> trelloListDtos = null;
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        Assert.assertEquals(0, trelloLists.size());
        Assert.assertNotNull(trelloLists);
    }

    @Test
    public void mapToListEmptyList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        Assert.assertEquals(0, trelloLists.size());
        Assert.assertNotNull(trelloLists);
    }

    @Test
    public void mapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("test1", "test1", true));
        trelloListDtos.add(new TrelloListDto("test2", "test2", false));
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        Assert.assertEquals(2, trelloLists.size());
    }

    @Test
    public void mapToListDtoNull() {
        //Given
        List<TrelloList> trelloLists = null;
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        Assert.assertEquals(0, trelloListDtos.size());
        Assert.assertNotNull(trelloListDtos);
    }

    @Test
    public void mapToListDtoEmptyList() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        Assert.assertEquals(0, trelloListDtos.size());
        Assert.assertNotNull(trelloListDtos);
    }

    @Test
    public void mapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("test1", "test1", true));
        trelloLists.add(new TrelloList("test2", "test2", false));
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        Assert.assertEquals(2, trelloListDtos.size());
    }

    @Test
    public void mapToCardDtoNull() {
        //Given
        TrelloCard trelloCard = null;
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertNotNull(trelloCardDto);
        Assert.assertEquals(trelloCardDto, new TrelloCardDto("", "", "", ""));
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test1", "desc1", "pos1", "id1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertNotNull(trelloCardDto);
        Assert.assertEquals(trelloCardDto, new TrelloCardDto("test1", "desc1", "pos1", "id1"));
    }

    @Test
    public void mapToCardNull() {
        //Given
        TrelloCardDto trelloCardDto = null;
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertNotNull(trelloCard);
        Assert.assertEquals(trelloCard, new TrelloCard("", "", "", ""));
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test1", "desc1", "pos1", "id1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertNotNull(trelloCard);
        Assert.assertEquals(trelloCard, new TrelloCard("test1", "desc1", "pos1", "id1"));
    }
}