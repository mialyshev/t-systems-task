package com.javaschool.service.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * The interface Card service.
 *
 * @author Marat Ialyshev
 */
public interface CardService {

    /**
     * Add card entity.
     *
     * @param cardDto the card entity
     * @param userOwner user who adds the card
     */
    void addCard(CardRegisterDto cardDto, User userOwner);

    /**
     * Search for all saved user cards.
     *
     * @param userId user id
     * @return the list
     */
    List<CardDto> getAllByUserId(long userId);

    /**
     * Find by id card entity.
     *
     * @param id the id
     * @return the card entity
     */
    CardDto getById(long id);

    /**
     * Controller for adding card.
     *
     * @param bindingResult bindingResult
     * @param cardRegisterDto the card entity
     * @param model the model on which we put the required data
     *
     * @return the title of the htm page to display
     */
    String registerNewCardController(BindingResult bindingResult, CardRegisterDto cardRegisterDto, Model model);
}
