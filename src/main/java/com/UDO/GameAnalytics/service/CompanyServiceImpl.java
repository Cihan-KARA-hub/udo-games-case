package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.repository.CompanyRepository;
import com.UDO.GameAnalytics.rules.CompanyRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.UDO.GameAnalytics.mapper.CompanyMapper.*;

@Service
@Validated
public class CompanyServiceImpl {
    private final CompanyRepository companyRepository;
    private final CompanyRule companyRule;

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);


    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyRule companyRule) {
        this.companyRepository = companyRepository;
        this.companyRule = companyRule;
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


}
