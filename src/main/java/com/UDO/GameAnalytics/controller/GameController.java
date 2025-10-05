package com.UDO.GameAnalytics.controller;

import com.UDO.GameAnalytics.dto.event.response.DailyTotalDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.dto.game.response.GameDto;
import com.UDO.GameAnalytics.dto.game.response.GameProfitResponseDto;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.service.GameServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/games/")
public class GameController {

    private final GameServiceImpl gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateGameResponseDto create(@RequestBody @Valid CreateGameRequestDto createGameRequestDto) {
        return gameService.createGame(createGameRequestDto);
    }

    @GetMapping
    public List<GameDto> getAllGames() {
      return   gameService.getAllGames();
    }
    @GetMapping("{id}/daily-revenues")
    public Page<DailyTotalDto> getByIdDailyRevenues(@PathVariable Long id,
                                                    @RequestParam(defaultValue = "3") int size,
                                                    @RequestParam(defaultValue = "0") int page) {

        return gameService.getByIdDailyRevenues(id,size,page);
    }
    @GetMapping("{gameId}/profit")
    @ResponseStatus(HttpStatus.OK)
    public GameProfitResponseDto profit(@PathVariable Long gameId){
        return  gameService.profit(gameId);
    }


}
