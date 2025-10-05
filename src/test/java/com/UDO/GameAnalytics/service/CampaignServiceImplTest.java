package com.UDO.GameAnalytics.service;


import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.entity.enums.Currency;
import com.UDO.GameAnalytics.repository.CampaignRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CampaignServiceImplTest {

    private CampaignRepository campaignRepository;
    private GameRule gameRule;
    private CampaignSpendServiceImpl campaignSpendService;
    private CampaignServiceImpl campaignService;

    @BeforeEach
    void setUp() {
        campaignRepository=mock(CampaignRepository.class);
        gameRule=mock(GameRule.class);
        campaignSpendService=mock(CampaignSpendServiceImpl.class);
        campaignService=new CampaignServiceImpl(campaignRepository,gameRule,campaignSpendService);
    }

    @Test
    void whenCreateCampaign_thenCampaignAndSpendSaved() {

        Long gameId = 2L;
        CreateCampaignRequestDto req = new CreateCampaignRequestDto();
        req.setName("Black Friday Promo TEST");
        req.setPrice(BigDecimal.valueOf(500));
        req.setDefaultCurrency(Currency.USD);
        req.setStartDate(OffsetDateTime.now());
        req.setEndDate(OffsetDateTime.now());

        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setName("Black Friday Promo TEST");
        campaign.setGameId(gameId);
        campaign.setName(req.getName());
        campaign.setStartDate(OffsetDateTime.now());
        campaign.setEndDate(OffsetDateTime.now().plusDays(5));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);
        CreateCampaignResponseDto response = campaignService.create(req, gameId);
        verify(gameRule, times(1)).findExistById(gameId);
        verify(campaignRepository, times(1)).save(any(Campaign.class));
        verify(campaignSpendService, times(1)).create(any(CreateCampaignSpendRequestDto.class));
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Black Friday Promo TEST");
        assertThat(response.getCurrency()).isEqualTo(Currency.USD);
    }
    @Test
    void whenGetSpendGameId_thenReturnAggregatedDto() {
        Long gameId = 2L;
        Campaign campaign = new Campaign();
        campaign.setId(2L);
        campaign.setGameId(gameId);

        CampaignSpend spend1 = new CampaignSpend();
        spend1.setAmount(BigDecimal.valueOf(100));
        spend1.setCurrency(Currency.USD);
        spend1.setCampaign(campaign);

        CampaignSpend spend2 = new CampaignSpend();
        spend2.setAmount(BigDecimal.valueOf(200));
        spend2.setCurrency(Currency.USD);
        spend2.setCampaign(campaign);
        campaign.setSpends(Arrays.asList(spend1, spend2));
        when(campaignRepository.findByGameId(gameId)).thenReturn(List.of(campaign));
        CampaignSpendResponseDto response = campaignService.getSpendGameId(gameId);
        assertThat(response).isNotNull();
        assertThat(response.getGameId()).isEqualTo(gameId);
        assertThat(response.getTotalsByCurrency().get("USD")).isEqualTo(BigDecimal.valueOf(300));
    }
    @Test
    void whenGetSpend_thenReturnMap() {
        Long gameId = 30L;

        Campaign campaign = new Campaign();
        campaign.setId(3L);
        campaign.setGameId(gameId);

        CampaignSpend spend1 = new CampaignSpend();
        spend1.setAmount(BigDecimal.valueOf(50));
        spend1.setCurrency(Currency.EUR);
        spend1.setCampaign(campaign);

        CampaignSpend spend2 = new CampaignSpend();
        spend2.setAmount(BigDecimal.valueOf(150));
        spend2.setCurrency(Currency.EUR);
        spend2.setCampaign(campaign);
        campaign.setSpends(Arrays.asList(spend1, spend2));
        when(campaignRepository.findByGameId(gameId)).thenReturn(List.of(campaign));
        Map<String, BigDecimal> result = campaignService.getSpend(gameId);
        assertThat(result).isNotEmpty();
        assertThat(result.get("EUR")).isEqualTo(BigDecimal.valueOf(200));
    }
}

