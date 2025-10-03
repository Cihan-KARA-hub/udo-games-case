package com.UDO.GameAnalytics.dto.event.response;

import java.math.BigDecimal;

public class CreateEventResponseDto {
    private Long gameId;
    private BigDecimal amount;

    public CreateEventResponseDto(Long gameId, BigDecimal amount) {
        this.gameId = gameId;
        this.amount = amount;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
