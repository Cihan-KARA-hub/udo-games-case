package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.dto.CampaignSpend.response.CreateCampaignSpendResponseDto;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.mapper.CampaignSpendMapper;
import com.UDO.GameAnalytics.repository.CampaignSpendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.UDO.GameAnalytics.mapper.CampaignSpendMapper.toEntity;

@Service
@Validated
public class CampaignSpendServiceImpl {

    private final CampaignSpendRepository campaignSpendRepository;

    private static final Logger log = LoggerFactory.getLogger(CampaignSpendServiceImpl.class);

    public CampaignSpendServiceImpl(CampaignSpendRepository campaignSpendRepository) {
        this.campaignSpendRepository = campaignSpendRepository;

    }

    public void create(CreateCampaignSpendRequestDto dto) {
        CampaignSpend spend = toEntity(dto);
        campaignSpendRepository.save(spend);
        log.info("Campaign Spend Created Successfully");
    }
}
