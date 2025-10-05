package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.entity.enums.Currency;

import java.math.BigDecimal;
import java.util.Map;

public class CampaignMapper {

    public static Campaign createCampaignRequestDtoToEntity(CreateCampaignRequestDto createGameRequestDto, Long gameId) {
        Campaign campaign = new Campaign();
        campaign.setName(createGameRequestDto.getName());
        campaign.setStartDate(createGameRequestDto.getStartDate());
        campaign.setEndDate(createGameRequestDto.getEndDate());
        campaign.setGameId(gameId);
        return campaign;
    }

    public static CreateCampaignResponseDto entityToCreateCampaignResponseDto(Campaign campaign, BigDecimal price, Currency currency) {
        CreateCampaignResponseDto createCampaignResponseDto = new CreateCampaignResponseDto();
        createCampaignResponseDto.setName(campaign.getName());
        createCampaignResponseDto.setStartDate(campaign.getStartDate());
        createCampaignResponseDto.setEndDate(campaign.getEndDate());
        createCampaignResponseDto.setPrice(price);
        createCampaignResponseDto.setCurrency(currency);
        return createCampaignResponseDto;
    }

    public static CampaignSpendResponseDto entityToCampaignSpendResponseDto(Map<String, BigDecimal> totalsByCurrency, Long gameId) {
        CampaignSpendResponseDto dto = new CampaignSpendResponseDto();
        dto.setGameId(gameId);
        dto.setTotalsByCurrency(totalsByCurrency);
        return dto;
    }
}
