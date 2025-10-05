package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.repository.CampaignRepository;
import com.UDO.GameAnalytics.rules.GameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.UDO.GameAnalytics.mapper.CampaignMapper.*;
import static com.UDO.GameAnalytics.mapper.CampaignSpendMapper.toCreateCampaignSpendRequestDto;

@Service
@Validated
public class CampaignServiceImpl {
    private final CampaignRepository campaignRepository;
    private final GameRule gameRule;
    private final CampaignSpendServiceImpl campaignSpendService;
    private static final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    public CampaignServiceImpl(CampaignRepository campaignRepository, GameRule gameRule, CampaignSpendServiceImpl campaignSpendService) {
        this.campaignRepository = campaignRepository;
        this.gameRule = gameRule;

        this.campaignSpendService = campaignSpendService;
    }

    public CreateCampaignResponseDto create(CreateCampaignRequestDto campaignSpend, Long gameId) {
        gameRule.findExistById(gameId);
        Campaign campaign = createCampaignRequestDtoToEntity(campaignSpend, gameId);
        campaign = campaignRepository.save(campaign);
        log.debug("Created campaign {}", campaign);
        CreateCampaignSpendRequestDto createCampaignSpendRequestDto = toCreateCampaignSpendRequestDto(campaign, campaignSpend.getPrice(), campaignSpend.getDefaultCurrency());
        campaignSpendService.create(createCampaignSpendRequestDto);
        return entityToCreateCampaignResponseDto(campaign, campaignSpend.getPrice(), campaignSpend.getDefaultCurrency());
    }

    @Transactional
    public CampaignSpendResponseDto getSpendGameId(Long gameId) {
        List<Campaign> campaignList = campaignRepository.findByGameId(gameId);

        Map<String, BigDecimal> amountByCurrency = new HashMap<>();

        for (Campaign campaign : campaignList) {
            for (CampaignSpend spend : campaign.getSpends()) {
                if (spend.getAmount() != null && spend.getCurrency() != null) {
                    String currency = spend.getCurrency().name();
                    BigDecimal currentAmount = amountByCurrency.getOrDefault(currency, BigDecimal.ZERO);
                    amountByCurrency.put(currency, currentAmount.add(spend.getAmount()));
                }
            }
        }

        return entityToCampaignSpendResponseDto(amountByCurrency, gameId);
    }

    @Transactional
    public Map<String, BigDecimal> getSpend(Long gameId) {
        List<Campaign> campaignList = campaignRepository.findByGameId(gameId);

        Map<String, BigDecimal> amountByCurrency = new HashMap<>();

        for (Campaign campaign : campaignList) {
            for (CampaignSpend spend : campaign.getSpends()) {
                if (spend.getAmount() != null && spend.getCurrency() != null) {
                    String currency = spend.getCurrency().name();
                    BigDecimal currentAmount = amountByCurrency.getOrDefault(currency, BigDecimal.ZERO);
                    amountByCurrency.put(currency, currentAmount.add(spend.getAmount()));
                }
            }
        }

        return amountByCurrency;
    }


}
