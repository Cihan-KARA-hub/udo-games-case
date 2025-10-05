package com.UDO.GameAnalytics.dto.event.response;


import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyTotalDto {
    private LocalDate date;
    private BigDecimal totalAmount;

    public DailyTotalDto(LocalDate date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
