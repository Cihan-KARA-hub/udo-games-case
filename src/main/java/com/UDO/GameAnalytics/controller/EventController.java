package com.UDO.GameAnalytics.controller;

import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.dto.game.response.GameDto;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.service.EventServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
public class EventController {
    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEventResponseDto createEventRequestDto(@RequestBody @Valid CreateEventRequestDto createEventRequestDto) {
        return eventService.createEvent(createEventRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RevenuesResponseDto> getAllGames() {
        return eventService.getEventAll();
    }
}
