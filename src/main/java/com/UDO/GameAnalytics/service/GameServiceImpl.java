package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.dto.game.response.GameDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Event;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.mapper.EventMapper;
import com.UDO.GameAnalytics.repository.GameRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.UDO.GameAnalytics.mapper.GameMapper.createReqToEntity;
import static com.UDO.GameAnalytics.mapper.GameMapper.entityToResponseDto;

@Service
@Validated
public class GameServiceImpl {
    private final GameRepository gameRepository;
    private final CompanyServiceImpl companyServiceImpl;
    private final GameRule gameRule;
    private final EventServiceImpl eventServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);


    public GameServiceImpl(GameRepository gameRepository, CompanyServiceImpl companyServiceImpl, GameRule gameRule, EventServiceImpl eventServiceImpl) {
        this.gameRepository = gameRepository;
        this.companyServiceImpl = companyServiceImpl;
        this.gameRule = gameRule;
        this.eventServiceImpl = eventServiceImpl;
    }


    public CreateGameResponseDto createGame(CreateGameRequestDto createGameRequestDto) {
        gameRule.findExistByGameName(createGameRequestDto.getName());
        Company company = companyServiceImpl.getCompany(createGameRequestDto.getCompanyId());
        Game game = createReqToEntity(createGameRequestDto, company);
        gameRepository.save(game);
        log.info("Game created successfully{}", game.toString());
        return entityToResponseDto(game);
    }

    public void getGame(Long gameId) {
         gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream()
                .map(GameDto::new)
                .toList();
    }


    public Page<RevenuesResponseDto> getByIdDailyRevenues(Long id, int size, int page) {
        gameRule.findExistById(id);
        Page<Event> events = eventServiceImpl.getPageRevenues(id, size, page);
        return events.map(EventMapper::entityToRevenuesResponseDto);
    }
}
