package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.entity.enums.Currency;

import java.math.BigDecimal;

public class CampaignSpendMapper {

    public static CreateCampaignSpendRequestDto toCreateCampaignSpendRequestDto(
            Campaign campaign,
            BigDecimal decimal,
            Currency currency) {
        CreateCampaignSpendRequestDto createCampaignSpendRequestDto = new CreateCampaignSpendRequestDto();
        createCampaignSpendRequestDto.setCampaign(campaign);
        createCampaignSpendRequestDto.setAmount(decimal);
        createCampaignSpendRequestDto.setCurrency(currency);
        return createCampaignSpendRequestDto;
    }

    public static CampaignSpend toEntity(CreateCampaignSpendRequestDto dto) {
        CampaignSpend campaignSpend = new CampaignSpend();
        campaignSpend.setAmount(dto.getAmount());
        campaignSpend.setCampaign(dto.getCampaign());
        campaignSpend.setCurrency(dto.getCurrency());
        return campaignSpend;
    }
}
