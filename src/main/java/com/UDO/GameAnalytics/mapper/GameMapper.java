package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;

public class GameMapper {
    public static Game createReqToEntity(CreateGameRequestDto createGameRequestDto, Company company) {
        Game game = new Game();
        game.setCompany(company);
        game.setName(createGameRequestDto.getName() );
        return game;
    }

    public static CreateGameResponseDto entityToResponseDto(Game game) {
        return new CreateGameResponseDto(game.getName(), "Game created successfully");
    }
}
