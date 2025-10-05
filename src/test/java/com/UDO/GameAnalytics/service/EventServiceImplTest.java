package com.UDO.GameAnalytics.service;


import com.UDO.GameAnalytics.core.exception.type.BusinessException;
import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;

import com.UDO.GameAnalytics.entity.enums.Currency;
import com.UDO.GameAnalytics.entity.enums.IncomeType;
import com.UDO.GameAnalytics.repository.EventRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceImplTest {
    private EventRepository eventRepository;
    private GameRule gameRule;
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        gameRule = mock(GameRule.class);
        eventService = new EventServiceImpl(eventRepository, gameRule);
    }

    @Test
    void whenCreateEvent_thenReturnResponseDto() {
        // given
        CreateEventRequestDto req = new CreateEventRequestDto();
        req.setGameId(1L);
        req.setAmount(BigDecimal.valueOf(100));
        req.setCurrency(Currency.USD);
        req.setType(IncomeType.PURCHASES);

        Company company = new Company();
        company.setId(99L);

        Game game = new Game();
        game.setId(1L);
        game.setCompany(company);

        Event event = new Event();
        event.setId(1L);
        event.setGame(game);
        event.setAmount(req.getAmount());
        event.setCurrency(req.getCurrency());

        when(gameRule.findExistById(1L)).thenReturn(game);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        CreateEventResponseDto response = eventService.createEvent(req);
        verify(gameRule, times(1)).findExistById(1L);
        verify(eventRepository, times(1)).save(any(Event.class));
        assertThat(response).isNotNull();
        assertThat(response.getGameId()).isEqualTo(1L);
        assertThat(response.getAmount()).isEqualTo(BigDecimal.valueOf(100));
    }


    @Test
    void whenGetEventAll_thenReturnList() {
        Event e1 = new Event();
        e1.setId(1L);
        e1.setAmount(BigDecimal.valueOf(50));
        e1.setCurrency(Currency.USD);

        when(eventRepository.findAll()).thenReturn(List.of(e1));

        List<RevenuesResponseDto> result = eventService.getEventAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getRevenue()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void whenGetPageRevenues_andEventNotFound_thenThrowBusinessException() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> eventService.getPageRevenues(99L, 10, 0));
    }

    @Test
    void whenFindByGameIdTotalAmount_thenReturnAggregatedMap() {
        Event e1 = new Event();
        e1.setAmount(BigDecimal.valueOf(100));
        e1.setCurrency(Currency.USD);

        Event e2 = new Event();
        e2.setAmount(BigDecimal.valueOf(50));
        e2.setCurrency(Currency.USD);

        when(eventRepository.findByGameId(1L)).thenReturn(List.of(e1, e2));

        Map<String, BigDecimal> result = eventService.findByGameIdTotalAmount(1L);

        assertThat(result).containsKey("USD");
        assertThat(result.get("USD")).isEqualTo(BigDecimal.valueOf(150));
    }

    @Test
    void whenGetPageRevenues_thenReturnPage() {
        Event e1 = new Event();
        e1.setId(1L);
        PageImpl<Event> page = new PageImpl<>(List.of(e1));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(e1));
        when(eventRepository.findDailyTotalsByGameId(eq(1L), any(PageRequest.class)))
                .thenReturn(Page.empty());
        Page<?> result = eventService.getPageRevenues(1L, 10, 0);
        assertThat(result).isNotNull();
    }
}

