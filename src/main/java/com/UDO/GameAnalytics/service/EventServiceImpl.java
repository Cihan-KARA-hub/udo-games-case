package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
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

import java.util.List;

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

    public Page<Event> getPageRevenues(Long id, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findByGameId(id, pageable);

    }
}
