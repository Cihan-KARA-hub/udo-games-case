package com.UDO.GameAnalytics.service;

import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.repository.CompanyRepository;
import com.UDO.GameAnalytics.rules.CompanyRule;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.UDO.GameAnalytics.mapper.CompanyMapper.*;

@Service
public class CompanyServiceImpl {
    private final CompanyRepository companyRepository;
    private final CompanyRule companyRule;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyRule companyRule) {
        this.companyRepository = companyRepository;
        this.companyRule = companyRule;
    }


    public CreateCompanyResponseDto createCompany(CreateCompanyRequestDto createCompanyRequestDto) {
       Integer nameCounter = companyExists(createCompanyRequestDto.getName());
        Company company = createCompanyRequestDtoToEntity(createCompanyRequestDto,nameCounter);
        company = companyRepository.save(company);
        return entityToCreateCompanyResponseDto(company);
    }

    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("company not exist"));
    }
    private Integer companyExists(String name) {
        List<Company> company = companyRepository.findByName(name);
        return company.size();
    }
}
