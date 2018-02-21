package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloValidatorTest {

    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void validateTrelloBoardsFilter(){
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("id1","name1",new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("id2","Test",new ArrayList<>()));
        //When
        trelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        Assert.assertEquals(1,trelloBoards.size());
        Assert.assertEquals("id1",trelloBoards.get(0).getId());
    }

    @Test
    public void validateTrelloBoardsFilterNone(){
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("id1","name1",new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("id2","name2",new ArrayList<>()));
        //When
        trelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        Assert.assertEquals(2,trelloBoards.size());
    }

    @Test
    public void validateTrelloBoardsNull(){
        //Given
        List<TrelloBoard> trelloBoards = null;
        //When
        trelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        Assert.assertNull(trelloBoards);
    }

}