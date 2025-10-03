package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;

public class EventMapper {


    public static Event createEventRequestDtoToEntity(CreateEventRequestDto createEventRequestDto, Game game) {
        Event event = new Event();
        event.setGame(game);
        event.setType(createEventRequestDto.getType());
        event.setAmount(createEventRequestDto.getAmount());
        return event;
    }

    public static CreateEventResponseDto entityToCreateEventResponseDto(Event event) {
        return new CreateEventResponseDto(event.getGame().getId(), event.getAmount());
    }
}
