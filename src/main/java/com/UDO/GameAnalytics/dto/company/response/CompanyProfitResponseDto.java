package com.UDO.GameAnalytics.dto.company.response;

import java.math.BigDecimal;
import java.util.Map;

public class CompanyProfitResponseDto {
    private Long companyId;
    private Map<String, BigDecimal> profit;

    public CompanyProfitResponseDto(Long companyId, Map<String, BigDecimal> profit) {
        this.companyId = companyId;
        this.profit = profit;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Map<String, BigDecimal> getProfit() {
        return profit;
    }

    public void setProfit(Map<String, BigDecimal> profit) {
        this.profit = profit;
    }
}
