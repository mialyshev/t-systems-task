package com.javaschool.service.impl.user;

import com.javaschool.dto.card.CardDto;
import com.javaschool.dto.card.CardRegisterDto;
import com.javaschool.entity.Card;
import com.javaschool.exception.UserException;
import com.javaschool.mapper.user.CardMapperImpl;
import com.javaschool.repository.user.CardRepository;
import com.javaschool.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapperImpl cardMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card getCardEntity(){
        return Card.builder()
                .id(1)
                .code("111")
                .number("1111222233334444")
                .owner("Test User")
                .validatyDate(LocalDate.of(2000, 1, 1))
                .build();
    }

    private CardDto getCardDto(){
        return CardDto.builder()
                .id(1)
                .validatyDate("2000-01-01")
                .lastNumbers("4444")
                .build();
    }

    private CardRegisterDto getCardRegisterDto(){
        return CardRegisterDto.builder()
                .code("111")
                .number("1111222233334444")
                .owner("Test User")
                .validatyDate("2000-01-01")
                .build();
    }

    @Test
    public void getByIdTest() throws UserException {
        lenient().when(cardRepository.findById(anyInt())).thenReturn(getCardEntity());
        when(cardMapper.toDto(any())).thenReturn(getCardDto());
        CardDto result = cardService.getById(1);
        assertEquals(getCardDto(), result);
    }

    @Test
    public void getAllByUserIdTest() throws UserException {
        lenient().when(cardRepository.findAllByUserId(anyInt())).thenReturn(Collections.singletonList(getCardEntity()));
        when(cardMapper.toDtoList(any())).thenReturn(Collections.singletonList(getCardDto()));
        List<CardDto> result = cardService.getAllByUserId(1);
        assertEquals(1, result.size());
        CardDto dto = result.get(0);
        assertEquals(getCardDto(), dto);
    }

    @Test
    public void addCardTest() {
        cardService.addCard(getCardRegisterDto(), null);
        verify(cardRepository, only()).save(any(Card.class));
    }
}
