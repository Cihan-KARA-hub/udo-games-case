package com.UDO.GameAnalytics.dto.game.response;

import java.math.BigDecimal;
import java.util.Map;

public class GameProfitResponseDto {
    private Long gameId;
    private Map<String, BigDecimal> profit;

    public GameProfitResponseDto(Long gameId, Map<String, BigDecimal> profit) {
        this.gameId = gameId;
        this.profit = profit;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Map<String, BigDecimal> getProfit() {
        return profit;
    }

    public void setProfit(Map<String, BigDecimal> profit) {
        this.profit = profit;
    }
}
