package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.core.exception.type.BusinessException;
import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.dto.event.response.DailyTotalDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.mapper.EventMapper;
import com.UDO.GameAnalytics.repository.EventRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.UDO.GameAnalytics.mapper.EventMapper.*;

@Service
@Validated
public class EventServiceImpl {

    private final EventRepository eventRepository;
    private final GameRule gameRule;

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);


    public EventServiceImpl(EventRepository eventRepository, GameRule gameRule) {
        this.eventRepository = eventRepository;


        this.gameRule = gameRule;
    }

    public CreateEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) {
        Game game = gameRule.findExistById(createEventRequestDto.getGameId());
        Event event = createEventRequestDtoToEntity(createEventRequestDto, game);
        eventRepository.save(event);
        log.info("Event created successfully{}", event.toString());
        return entityToCreateEventResponseDto(event);
    }

    public List<RevenuesResponseDto> getEventAll() {
        return eventRepository.findAll()
                .stream()
                .map(EventMapper::entityToRevenuesResponseDto)
                .toList();
    }

    public Page<DailyTotalDto> getPageRevenues(Long id, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        eventRepository.findById(id).orElseThrow(()-> new BusinessException("Evet not found"));
        return eventRepository.findDailyTotalsByGameId(id, pageable);
    }

    public  Map<String,BigDecimal> findByGameIdTotalAmount(Long gameId) {
      List<Event> eventList= eventRepository.findByGameId(gameId);
        Map<String,BigDecimal> amountByCurrency = new HashMap<>();
        for (Event event : eventList) {
            if(event.getAmount()!=null && event.getCurrency()!=null){
                String curr=event.getCurrency().name();
                BigDecimal currentAmount = amountByCurrency.getOrDefault(curr, BigDecimal.ZERO);
                amountByCurrency.put(curr, currentAmount.add(event.getAmount()));

            }
        }
        return amountByCurrency;
    }
}
