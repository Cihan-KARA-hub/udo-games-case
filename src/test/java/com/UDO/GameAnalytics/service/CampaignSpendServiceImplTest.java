package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.CampaignSpend.request.CreateCampaignSpendRequestDto;
import com.UDO.GameAnalytics.entity.Campaign;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.entity.enums.Currency;
import com.UDO.GameAnalytics.repository.CampaignSpendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CampaignSpendServiceImplTest {

    private  CampaignSpendRepository campaignSpendRepository;
    private CampaignSpendServiceImpl campaignSpendService;

    @BeforeEach
    void setUp() {
        campaignSpendRepository= Mockito.mock(CampaignSpendRepository.class);
        campaignSpendService = new CampaignSpendServiceImpl(campaignSpendRepository);
    }
    @Test
    public void whenCreateCampaignSpendRequestDto_thenSuccess() {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setGameId(2L);
        campaign.setName("New Year Promo Test");
        campaign.setStartDate(OffsetDateTime.now().minusDays(10));
        campaign.setEndDate(OffsetDateTime.now().plusDays(20));

        CreateCampaignSpendRequestDto dto = new CreateCampaignSpendRequestDto();
        dto.setAmount(BigDecimal.valueOf(100));
        dto.setCurrency(Currency.USD);
        dto.setDescription("test");
        dto.setCampaign(campaign);

        when(campaignSpendRepository.save(any(CampaignSpend.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        campaignSpendService.create(dto);
        ArgumentCaptor<CampaignSpend> captor = ArgumentCaptor.forClass(CampaignSpend.class);
        verify(campaignSpendRepository, times(1)).save(captor.capture());
        CampaignSpend savedSpend = captor.getValue();
        assertThat(savedSpend).isNotNull();
        assertThat(savedSpend.getAmount()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(savedSpend.getCurrency()).isEqualTo(Currency.USD);
        assertThat(savedSpend.getCampaign().getId()).isEqualTo(1L);
    }
}