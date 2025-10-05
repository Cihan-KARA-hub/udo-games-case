package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.event.response.DailyTotalDto;
import com.UDO.GameAnalytics.dto.event.response.RevenuesResponseDto;
import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.dto.game.response.GameDto;
import com.UDO.GameAnalytics.dto.game.response.GameProfitResponseDto;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.UDO.GameAnalytics.mapper.GameMapper.createReqToEntity;
import static com.UDO.GameAnalytics.mapper.GameMapper.entityToResponseDto;

@Service
@Validated
public class GameServiceImpl {
    private final GameRepository gameRepository;
    private final CompanyServiceImpl companyServiceImpl;
    private final GameRule gameRule;
    private final EventServiceImpl eventServiceImpl;
    private final CampaignServiceImpl campaignServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);


    public GameServiceImpl(GameRepository gameRepository, CompanyServiceImpl companyServiceImpl, GameRule gameRule, EventServiceImpl eventServiceImpl, CampaignServiceImpl campaignServiceImpl) {
        this.gameRepository = gameRepository;
        this.companyServiceImpl = companyServiceImpl;
        this.gameRule = gameRule;
        this.eventServiceImpl = eventServiceImpl;
        this.campaignServiceImpl = campaignServiceImpl;
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


    public Page<DailyTotalDto> getByIdDailyRevenues(Long id, int size, int page) {
        gameRule.findExistById(id);
         return   eventServiceImpl.getPageRevenues(id, size, page);
    }

    public GameProfitResponseDto profit(Long gameId) {
        gameRule.findExistById(gameId);
        Map<String, BigDecimal> total = calculateNetIncome(gameId);
        return new GameProfitResponseDto(gameId, total);
    }

    public Map<String, BigDecimal> calculateNetIncome(Long gameId) {
        Map<String, BigDecimal> incomes = eventServiceImpl.findByGameIdTotalAmount(gameId);
        Map<String, BigDecimal> spends = campaignServiceImpl.getSpendGameId(gameId).getTotalsByCurrency();
        Map<String, BigDecimal> netByCurrency = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : incomes.entrySet()) {
            String currency = entry.getKey();
            BigDecimal incomeAmount = entry.getValue();
            BigDecimal spendAmount = spends.getOrDefault(currency, BigDecimal.ZERO);
            netByCurrency.put(currency, incomeAmount.subtract(spendAmount));
        }
        for (Map.Entry<String, BigDecimal> entry : spends.entrySet()) {
            String currency = entry.getKey();
            if (!netByCurrency.containsKey(currency)) {
                netByCurrency.put(currency, BigDecimal.ZERO.subtract(entry.getValue()));
            }
        }
        return netByCurrency;
    }

}
