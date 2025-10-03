package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.GameRepository;
import com.UDO.GameAnalytics.rules.CompanyRule;
import com.UDO.GameAnalytics.rules.GameRule;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.UDO.GameAnalytics.mapper.GameMapper.createReqToEntity;
import static com.UDO.GameAnalytics.mapper.GameMapper.entityToResponseDto;

@Service
public class GameServiceImpl {
    private final GameRepository gameRepository;
    private final CompanyServiceImpl companyServiceImpl;
    private final GameRule gameRule;

    public GameServiceImpl(GameRepository gameRepository, CompanyServiceImpl companyServiceImpl, GameRule gameRule) {
        this.gameRepository = gameRepository;
        this.companyServiceImpl = companyServiceImpl;
        this.gameRule = gameRule;
    }


    public CreateGameResponseDto createGame(CreateGameRequestDto createGameRequestDto) {
        Company company = companyServiceImpl.getCompany(createGameRequestDto.getCompanyId());
        Integer gameName = gameNameList(createGameRequestDto.getName());
        Game game = createReqToEntity(createGameRequestDto, company, gameName);
        gameRepository.save(game);
        return entityToResponseDto(game);
    }

    private Integer gameNameList(String name) {
        List<Game> gameList = gameRepository.findByName(name);
        return gameList.size();
    }
}
