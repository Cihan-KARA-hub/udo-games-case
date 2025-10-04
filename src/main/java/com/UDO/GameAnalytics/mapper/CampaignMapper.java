package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.Campaign;

import java.math.BigDecimal;

public class CampaignMapper {

    public static Campaign createCampaignRequestDtoToEntity(CreateCampaignRequestDto createGameRequestDto, Long gameId) {
        Campaign campaign = new Campaign();
        campaign.setName(createGameRequestDto.getName());
        campaign.setStartDate(createGameRequestDto.getStartDate());
        campaign.setEndDate(createGameRequestDto.getEndDate());
        campaign.setGameId(gameId);


        return campaign;
    }

    public static CreateCampaignResponseDto entityToCreateCampaignResponseDto(Campaign campaign, BigDecimal price) {
        CreateCampaignResponseDto createCampaignResponseDto = new CreateCampaignResponseDto();
        createCampaignResponseDto.setName(campaign.getName());
        createCampaignResponseDto.setStartDate(campaign.getStartDate());
        createCampaignResponseDto.setEndDate(campaign.getEndDate());
        createCampaignResponseDto.setPrice(price);
        return createCampaignResponseDto;
    }
}
