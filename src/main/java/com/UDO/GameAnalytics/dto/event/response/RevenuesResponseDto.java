package com.UDO.GameAnalytics.dto.event.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class RevenuesResponseDto {
    private OffsetDateTime timestamp;
    private BigDecimal revenue;

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
