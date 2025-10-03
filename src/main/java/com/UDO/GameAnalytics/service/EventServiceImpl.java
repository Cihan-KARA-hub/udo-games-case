package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.EventRepository;
import org.springframework.stereotype.Service;

import static com.UDO.GameAnalytics.mapper.EventMapper.createEventRequestDtoToEntity;
import static com.UDO.GameAnalytics.mapper.EventMapper.entityToCreateEventResponseDto;
@Service
public class EventServiceImpl {
    private final EventRepository eventRepository;
    private final GameServiceImpl gameService;

    public EventServiceImpl(EventRepository eventRepository, GameServiceImpl gameService) {
        this.eventRepository = eventRepository;
        this.gameService = gameService;
    }

    public CreateEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) {
        Game game = gameService.getGame(createEventRequestDto.getGameId());
        Event event = createEventRequestDtoToEntity(createEventRequestDto, game);
        eventRepository.save(event);
        return entityToCreateEventResponseDto(event);
    }
}
