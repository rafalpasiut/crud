package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtos) {
        if (trelloBoardDtos != null) {
            return trelloBoardDtos.stream()
                    .map(trelloBoardDto -> mapToBoard(trelloBoardDto))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        if (trelloBoards != null) {
            return trelloBoards.stream()
                    .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListDto(trelloBoard.getLists())))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDtos) {
        if (trelloListDtos != null) {
            return trelloListDtos.stream()
                    .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        if (trelloLists != null) {
            return trelloLists.stream()
                    .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        if (trelloCard != null) {
            return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPosition(), trelloCard.getListId());
        } else {
            return new TrelloCardDto("", "", "", "");
        }
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        if (trelloCardDto != null) {
            return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(), trelloCardDto.getListId());
        } else {
            return new TrelloCard("", "", "", "");
        }
    }

    private TrelloBoard mapToBoard(final TrelloBoardDto trelloBoardDto) {
        return new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToList(trelloBoardDto.getLists()));
    }


}
