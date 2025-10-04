package com.UDO.GameAnalytics.dto.campaign.response;

import com.UDO.GameAnalytics.entity.enums.Currency;

import java.math.BigDecimal;
import java.util.Map;

public class CampaignSpendResponseDto {
    private Long gameId;
    private Map<String, BigDecimal> totalsByCurrency;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Map<String, BigDecimal> getTotalsByCurrency() {
        return totalsByCurrency;
    }

    public void setTotalsByCurrency(Map<String, BigDecimal> totalsByCurrency) {
        this.totalsByCurrency = totalsByCurrency;
    }
}