package com.UDO.GameAnalytics.dto.campaign.response;

import java.math.BigDecimal;

public class CampaignSpendResponseDto {
    private Long gameId;
    private BigDecimal spent;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
}
