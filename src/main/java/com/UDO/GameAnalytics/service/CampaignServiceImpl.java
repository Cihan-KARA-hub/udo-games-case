package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.mapper.CampaignMapper;
import com.UDO.GameAnalytics.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

import static com.UDO.GameAnalytics.mapper.CampaignMapper.createCampaignRequestDtoToEntity;
import static com.UDO.GameAnalytics.mapper.CampaignMapper.entityToCreateCampaignResponseDto;
import static com.UDO.GameAnalytics.mapper.CampaignSpendMapper.toCreateCampaignSpendRequestDto;

@Service
@Validated
public class CampaignServiceImpl {
    private final CampaignRepository campaignRepository;
    private final GameServiceImpl gameService;
    private final CampaignSpendServiceImpl campaignSpendService;
    private static final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    public CampaignServiceImpl(CampaignRepository campaignRepository, GameServiceImpl gameService, CampaignSpendServiceImpl campaignSpendService) {
        this.campaignRepository = campaignRepository;
        this.gameService = gameService;
        this.campaignSpendService = campaignSpendService;
    }

    public CreateCampaignResponseDto create(CreateCampaignRequestDto campaignSpend, Long gameId) {
        gameService.getGame(gameId);
        Campaign campaign = createCampaignRequestDtoToEntity(campaignSpend, gameId);
        campaign = campaignRepository.save(campaign);
        log.debug("Created campaign {}", campaign);
        CreateCampaignSpendRequestDto createCampaignSpendRequestDto = toCreateCampaignSpendRequestDto(campaign, campaignSpend.getPrice(),campaignSpend.getDefaultCurrency());
        campaignSpendService.create(createCampaignSpendRequestDto);
        return entityToCreateCampaignResponseDto(campaign, campaignSpend.getPrice());
    }
    @Transactional
    public CampaignSpendResponseDto getSpendGameId(Long gameId) {
        List<Campaign> campaignList = campaignRepository.findByGameId(gameId);
        BigDecimal totalAmount = campaignList.stream()
                .flatMap(campaign -> campaign.getSpends().stream())
                .map(CampaignSpend::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CampaignSpendResponseDto response = new CampaignSpendResponseDto();
        response.setSpent(totalAmount);
        response.setGameId(gameId);
        return response;
    }

}
