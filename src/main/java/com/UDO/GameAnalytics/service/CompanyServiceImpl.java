package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CompanyProfitResponseDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.CompanyRepository;
import com.UDO.GameAnalytics.rules.CompanyRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.UDO.GameAnalytics.mapper.CompanyMapper.*;

@Service
@Validated
public class CompanyServiceImpl {
    private final CompanyRepository companyRepository;
    private final CompanyRule companyRule;
    private final GameServiceImpl gameService;

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);


    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyRule companyRule, @Lazy GameServiceImpl gameService) {
        this.companyRepository = companyRepository;
        this.companyRule = companyRule;
        this.gameService = gameService;
    }


    public CreateCompanyResponseDto createCompany(CreateCompanyRequestDto createCompanyRequestDto) {
        companyRule.findExistById(createCompanyRequestDto.getName());
        Company company = createCompanyRequestDtoToEntity(createCompanyRequestDto);
        company = companyRepository.save(company);
        log.info("Company created successfully{}", company.toString());
        return entityToCreateCompanyResponseDto(company);
    }

    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("company not exist"));
    }

    @Transactional
    public CompanyProfitResponseDto profit(Long companyId) {
        Company com = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("company not exist"));
        Map<String, BigDecimal> totalAmount = calculateNetIncome(com.getGames());
        return new CompanyProfitResponseDto(com.getId(), totalAmount);
    }

    public Map<String, BigDecimal> calculateNetIncome(List<Game> games) {
        Map<String, BigDecimal> netByCurrency = new HashMap<>();

        for (Game game : games) {
            Map<String, BigDecimal> gameNet = gameService.calculateNetIncome(game.getId());

            for (Map.Entry<String, BigDecimal> entry : gameNet.entrySet()) {
                String currency = entry.getKey();
                BigDecimal current = netByCurrency.getOrDefault(currency, BigDecimal.ZERO);
                netByCurrency.put(currency, current.add(entry.getValue()));
            }
        }

        return netByCurrency;
    }

}
