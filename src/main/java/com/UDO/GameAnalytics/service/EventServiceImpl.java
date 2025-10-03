package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.mapper.EventMapper;
import com.UDO.GameAnalytics.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.UDO.GameAnalytics.mapper.EventMapper.*;

@Service
public class EventServiceImpl {

    private final EventRepository eventRepository;
    private final GameServiceImpl gameService;

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);


    public EventServiceImpl(EventRepository eventRepository, GameServiceImpl gameService) {
        this.eventRepository = eventRepository;
        this.gameService = gameService;
    }

    public CreateEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) {
        Game game = gameService.getGame(createEventRequestDto.getGameId());
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
