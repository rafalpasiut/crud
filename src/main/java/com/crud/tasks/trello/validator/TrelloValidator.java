package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    public void validateCard(final TrelloCard trelloCard) {
        if (trelloCard != null) {
            if (trelloCard.getName().contains("test")) {
                LOGGER.info("Someone is testing my application!");
            } else {
                LOGGER.info("Seems that my application is used in proper way.");
            }
        }
    }

    public List<TrelloBoard> validateTrelloBoards(List<TrelloBoard> trelloBoards) {
        if (trelloBoards != null) {
            LOGGER.info("Starting filtering boards...");
            trelloBoards = trelloBoards.stream()
                    .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                    .collect(Collectors.toList());
            LOGGER.info("Boards has been filtered. Current list size: " + trelloBoards.size());
        }
        return trelloBoards;
    }

}
