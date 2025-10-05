package com.UDO.GameAnalytics.dto.CampaignSpend.request;

import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.enums.Currency;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CreateCampaignSpendRequestDto {
    @NotNull
    private Campaign campaign;
    @NotNull
    @Positive
    @Min(1)
    private BigDecimal amount;
    @NotBlank
    private String description;
    @NotNull
    private Currency currency;


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateCampaignSpendRequestDto that = (CreateCampaignSpendRequestDto) o;
        return Objects.equals(campaign, that.campaign) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaign, amount, description, currency);
    }
}
