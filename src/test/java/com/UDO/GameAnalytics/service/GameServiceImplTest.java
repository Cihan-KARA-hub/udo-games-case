package com.UDO.GameAnalytics.service;


import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.game.request.CreateGameRequestDto;
import com.UDO.GameAnalytics.dto.game.response.CreateGameResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.GameRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GameServiceImplTest {
    private GameServiceImpl gameService;
    private GameRepository gameRepository;
    private CompanyServiceImpl companyServiceImpl;
    private GameRule gameRule;
    private EventServiceImpl eventServiceImpl;

    private CampaignServiceImpl campaignServiceImpl;

    @BeforeEach
    void setUp() {
        eventServiceImpl = mock(EventServiceImpl.class);
        campaignServiceImpl = mock(CampaignServiceImpl.class);
        companyServiceImpl = mock(CompanyServiceImpl.class);
        gameRule = mock(GameRule.class);
        gameRepository = mock(GameRepository.class);

    }

    @Test
    void whenCreateGame_thenReturnResponseDto() {
        CreateGameRequestDto requestDto = new CreateGameRequestDto();
        requestDto.setName("Test Game");
        requestDto.setCompanyId(1L);

        Company company = new Company();
        company.setId(1L);

        Game game = new Game();
        game.setId(100L);
        game.setCompany(company);
        game.setName(requestDto.getName());
        doNothing().when(gameRule).findExistByGameName(requestDto.getName());
        when(companyServiceImpl.getCompany(1L)).thenReturn(company);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        CreateGameResponseDto responseDto = gameService.createGame(requestDto);
        verify(gameRule, times(1)).findExistByGameName("Test Game");
        verify(companyServiceImpl, times(1)).getCompany(1L);
        verify(gameRepository, times(1)).save(any(Game.class));
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getMessage()).isEqualTo("Game created successfully");
    }

    @Test
    void whenCalculateNetIncome_thenReturnCorrectMap() {
        Long gameId = 1L;
        Map<String, java.math.BigDecimal> incomes = new HashMap<>();
        incomes.put("USD", java.math.BigDecimal.valueOf(100));
        incomes.put("EUR", java.math.BigDecimal.valueOf(200));

        Map<String, java.math.BigDecimal> spends = new HashMap<>();
        spends.put("USD", java.math.BigDecimal.valueOf(30));
        spends.put("GBP", java.math.BigDecimal.valueOf(50));

        when(eventServiceImpl.findByGameIdTotalAmount(gameId)).thenReturn(incomes);

        CampaignSpendResponseDto spendResponse = new CampaignSpendResponseDto();
        spendResponse.setTotalsByCurrency(spends);
        when(campaignServiceImpl.getSpendGameId(gameId)).thenReturn(spendResponse);
        Map<String, java.math.BigDecimal> netIncome = gameService.calculateNetIncome(gameId);
        assertThat(netIncome.get("USD")).isEqualTo(java.math.BigDecimal.valueOf(70));
        assertThat(netIncome.get("EUR")).isEqualTo(java.math.BigDecimal.valueOf(200));
        assertThat(netIncome.get("GBP")).isEqualTo(java.math.BigDecimal.valueOf(-50));
    }

}
