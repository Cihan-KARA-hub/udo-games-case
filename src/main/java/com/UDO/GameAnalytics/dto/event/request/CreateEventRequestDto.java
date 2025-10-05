package com.UDO.GameAnalytics.dto.event.request;

import com.UDO.GameAnalytics.entity.enums.Currency;
import com.UDO.GameAnalytics.entity.enums.IncomeType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateEventRequestDto {
    @NotNull()
    private Long gameId;
    @NotNull()
    private IncomeType type;
    @NotNull()
    @Positive
    private BigDecimal amount;
    @NotNull
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public IncomeType getType() {
        return type;
    }

    public void setType(IncomeType type) {
        this.type = type;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }


}
