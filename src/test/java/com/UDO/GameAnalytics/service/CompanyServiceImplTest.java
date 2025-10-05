package com.UDO.GameAnalytics.service;


import com.UDO.GameAnalytics.core.exception.type.BusinessException;
import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CompanyProfitResponseDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.CompanyRepository;
import com.UDO.GameAnalytics.rules.CompanyRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    private CompanyRepository companyRepository;
    private CompanyRule companyRule;
    private GameServiceImpl gameService;
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyRule = mock(CompanyRule.class);
        gameService = mock(GameServiceImpl.class);
        companyService = new CompanyServiceImpl(companyRepository, companyRule, gameService);
    }

    @Test
    void whenCreateCompany_thenReturnResponseDto() {
        CreateCompanyRequestDto request = new CreateCompanyRequestDto();
        request.setName("Ubisoft");


        Company company = new Company();
        company.setId(1L);
        company.setName("Ubisoft");

        when(companyRepository.save(any(Company.class))).thenReturn(company);
        CreateCompanyResponseDto response = companyService.createCompany(request);
        verify(companyRule, times(1)).findExistById(request.getName());
        verify(companyRepository, times(1)).save(any(Company.class));

        assertThat(response).isNotNull();
        assertThat(response.getCompanyName()).isEqualTo("Ubisoft");
        assertThat(response.getMessage()).isEqualTo("Company created");
    }

    @Test
    void whenGetCompany_thenReturnCompany() {
        Company company = new Company();
        company.setId(10L);
        company.setName("EA");

        when(companyRepository.findById(10L)).thenReturn(Optional.of(company));

        Company result = companyService.getCompany(10L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("EA");
    }

    @Test
    void whenGetCompany_notFound_thenThrowException() {
        when(companyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompany(99L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("company not exist");
    }

    @Test
    void whenProfit_thenReturnCompanyProfit() {
        Company company = new Company();
        company.setId(5L);

        Game game1 = new Game();
        game1.setId(100L);
        Game game2 = new Game();
        game2.setId(200L);
        company.setGames(Arrays.asList(game1, game2));
        when(companyRepository.findById(5L)).thenReturn(Optional.of(company));
        when(gameService.calculateNetIncome(100L))
                .thenReturn(Map.of("USD", BigDecimal.valueOf(200)));
        when(gameService.calculateNetIncome(200L))
                .thenReturn(Map.of("USD", BigDecimal.valueOf(300)));
        CompanyProfitResponseDto response = companyService.profit(5L);
        assertThat(response).isNotNull();
        assertThat(response.getCompanyId()).isEqualTo(5L);
        assertThat(response.getProfit().get("USD")).isEqualByComparingTo(BigDecimal.valueOf(500));
    }

    @Test
    void whenCalculateNetIncome_thenAggregateByCurrency() {
        Game game1 = new Game();
        game1.setId(1L);
        Game game2 = new Game();
        game2.setId(2L);

        List<Game> games = Arrays.asList(game1, game2);

        when(gameService.calculateNetIncome(1L))
                .thenReturn(Map.of("USD", BigDecimal.valueOf(100)));
        when(gameService.calculateNetIncome(2L))
                .thenReturn(Map.of("USD", BigDecimal.valueOf(150), "EUR", BigDecimal.valueOf(50)));

        Map<String, BigDecimal> result = companyService.calculateNetIncome(games);

        assertThat(result.get("USD")).isEqualByComparingTo(BigDecimal.valueOf(250));
        assertThat(result.get("EUR")).isEqualByComparingTo(BigDecimal.valueOf(50));
    }
}

