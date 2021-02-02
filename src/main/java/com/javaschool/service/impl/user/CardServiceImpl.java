package com.javaschool.service.impl.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.dto.product.BrandDto;
import com.javaschool.entity.Card;
import com.javaschool.entity.User;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.user.CardMapperImpl;
import com.javaschool.repository.user.CardRepository;
import com.javaschool.repository.user.UserRepository;
import com.javaschool.service.user.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService  {

    private final CardRepository cardRepository;
    private final CardMapperImpl cardMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addCard(CardRegisterDto cardDto, User userOwner) {
        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setCode(cardDto.getCode());
        card.setOwner(cardDto.getOwner());
        card.setValidatyDate(getDate(cardDto.getValidatyDate()));
        card.setUser(userOwner);
        cardRepository.save(card);
    }

    @Override
    public List<CardDto> getAllByUserId(long userId) {
        List<CardDto> cardDtos = null;
        try {
            cardDtos = cardMapper.toDtoList(cardRepository.findAllByUserId(userId));
        } catch (UserException e) {
            log.error("Error getting all saved cards", e);
        }catch (Exception e) {
            log.error("Error at CardService.getAllByUserId()", e);
        }
        return cardDtos;
    }

    @Override
    public CardDto getById(long id) {
        CardDto cardDto = null;
        try {
            cardDto = cardMapper.toDto(cardRepository.findById(id));
        } catch (UserException e) {
            log.error("Error getting a card by id", e);
        }catch (Exception e) {
            log.error("Error at CardService.getById()", e);
        }
        return cardDto;
    }

    @Override
    @Transactional
    public String registerNewCardController(BindingResult bindingResult, CardRegisterDto cardRegisterDto, Model model) {
        if (bindingResult.hasErrors()) {
            return "card-register";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User userFromBd = null;
        try{
            userFromBd = userRepository.findByEmail(currentUser);
        }catch (UserException e){
            log.error("Error while getting user for register new card", e);
        }

        String[] ownerDate = cardRegisterDto.getOwner().split(" ");
        if (ownerDate.length != 2){
            model.addAttribute("ownerError", "Cardholder data must consist of first and last name");
            return "card-register";
        }
        addCard(cardRegisterDto, userFromBd);
        return "redirect:/profile/cards";
    }

    private LocalDate getDate(String date){
        String[] nums = date.split("-");
        return LocalDate.of(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), 1);
    }
}
